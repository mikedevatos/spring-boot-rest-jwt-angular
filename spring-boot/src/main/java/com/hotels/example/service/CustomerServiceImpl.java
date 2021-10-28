package com.hotels.example.service;

import com.hotels.example.errors.RoomCapacitySurpassedException;
import com.hotels.example.errors.RoomIsBookedException;
import com.hotels.example.model.*;
import com.hotels.example.repositories.CustomerRepo;
import com.hotels.example.util.DatesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CustomerServiceImpl{
    private CustomerRepo customerRepo;
    private RoomServiceImpl roomService;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo,
                               RoomServiceImpl roomService ) {
        this.customerRepo = customerRepo;
        this.roomService = roomService;
    }

    @Cacheable( cacheNames="customers",
                key="#page",
                cacheManager = "caffeineCacheManager",
                unless="#result.size() <= 0")
       public List<Customer> findAllbyPage(Integer page, int size){
               Pageable paging = PageRequest.of(page, size);
               Page<Customer> pagedCusto = customerRepo.findAll(paging);
               List<Customer> custo = pagedCusto.getContent();
               return custo;
           }

        public Customer findBy_Id(Integer id) {
        Customer customer = customerRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return customer;

       }

       public Optional<Customer> findById(Integer id){
           return   customerRepo.findById(id);
       }

    public long count(){
      return  customerRepo.count();
    }

    /** delete customer**/
    @Transactional
    @Caching( evict = {
            @CacheEvict(value="customers",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result == true")
    })
    public boolean delete (Integer id) {
            boolean idExists =customerRepo.existsById(id);
            customerRepo.deleteById(id);
            return  idExists;
    }

    /** updating customer **/
    @Transactional
    @Caching( evict = {
            @CacheEvict(value="customers",
                        allEntries = true,
                        cacheManager = "caffeineCacheManager",condition = "#result != null")
    })
    public Customer update(Customer custo){
      return  customerRepo.save(custo);
    }


    /** replacing customer room**/
    @Transactional(rollbackFor = { RoomIsBookedException.class, RoomCapacitySurpassedException.class  }  )
    @Caching( evict = {
            @CacheEvict(value="customers",allEntries = true,
                        cacheManager = "caffeineCacheManager",condition = "#result != null")
    }   )
    public Customer replaceRoom(Customer c, Room replacingRoom){

        final   LocalDate start =c.getBooking().getStartBooking().toLocalDate();
        final  LocalDate end =  c.getBooking().getEndBooking().toLocalDate();

        LocalDate startDate  =start;
        LocalDate endDate = end;

        if(  c.getPersons().size()  >  replacingRoom.getRoomCapacity() )
          throw  new RoomCapacitySurpassedException("total persons surpassed room capacity");

        /**get room bookings  and validation*/
        Set<Booking> bookings =  !replacingRoom.getCustomers().isEmpty()   ?
                                  replacingRoom.getCustomers()
                                 .stream()
                                 .map(Customer::getBooking)

                                    //validation not booked
                                 .filter( b-> !( end.isBefore(b.getStartBooking().toLocalDate())  ||
                                                 start.isAfter(b.getEndBooking().toLocalDate())      ))
                                 .collect(Collectors.toSet()) : new HashSet<>();

        log.debug("start booking date is: "+ startDate.toString() );
        log.debug("end booking date is: "+ endDate.toString() );

        /**get customer dates in (start day booking -> end day booking) range*/
        List<LocalDate> customerBookingDates = DatesUtil.getDates(startDate, endDate);

        AtomicBoolean isRoomBooked = new AtomicBoolean(false);

        /** compare customer dates with room bookings **/
        if( !bookings.isEmpty() ) {
            for (LocalDate date : customerBookingDates) {

                if (isRoomBooked.get())
                    break;

                for (Booking book : bookings) {

                    isRoomBooked.set(DatesUtil.isDateBooked(
                            date,
                            book.getStartBooking().toLocalDate(),
                            book.getEndBooking().toLocalDate()));

                    if (isRoomBooked.get())
                        break;
                }
            }
        }

        if( !isRoomBooked.get() ) {

            Customer custo =  findBy_Id( c.getId() );
            custo.setRoom(replacingRoom);

            float roomPrice = custo.getRoom().getPrice();
            log.debug("roomPrice bill is: "+ roomPrice);

            float bill  = ( roomPrice * customerBookingDates.size() );
            custo.setBill(bill);
            
            return  customerRepo.save(custo);
           }
        throw  new RoomIsBookedException("Room is already booked for this days!!!!");
    }

    /**  create customer**/
    @Transactional
    @CacheEvict(value="customers",allEntries=true,condition = "#result != null")
    public Customer create (Customer customer) {

        final LocalDate start = customer.getBooking().getStartBooking().toLocalDate();
        log.debug("start day is" +start.toString());

        final LocalDate end = customer.getBooking().getEndBooking().toLocalDate();
        log.debug("end day is" +end.toString());

        Room room = roomService.findBy_Id( customer.getRoom().getId() );

        if( room.getRoomCapacity() < customer.getPersons().size() )
            throw  new RoomCapacitySurpassedException("total persons surpassed room capacity");

            LocalDate startDate = start;
            LocalDate endDate = end;

        //get bookings of the room and filter bookings not in the range of customer dates
        List<Booking> bookings =    !room.getCustomers().isEmpty()   ?

                                     room.getCustomers()
                                    .stream()
                                    .map(Customer::getBooking)

                                    .filter( b->
                                          !( end.isBefore(b.getStartBooking().toLocalDate()) ||
                                             start.isAfter(b.getEndBooking().toLocalDate())     ) )


                                    .collect(Collectors.toList()) :  new ArrayList<>();

        AtomicBoolean isDayBooked = new AtomicBoolean(false);

        /**get dates in range for customer if bookings not empty **/
        List<LocalDate> customerBookingDates = DatesUtil.getDates(startDate, endDate);

        /** find if room is booked  for this customer booking dates **/
        if( !bookings.isEmpty() ) {
            for (LocalDate date : customerBookingDates) {

                if (isDayBooked.get())
                    break;

                for (Booking book : bookings) {

                    isDayBooked.set(DatesUtil.isDateBooked(
                            date,
                            book.getStartBooking().toLocalDate(),
                            book.getEndBooking().toLocalDate()));

                    if (isDayBooked.get())
                        break;
                }
            }
        }

            if ( !isDayBooked.get() )
            {
                customer.setRoom(room);
                Set<Person> persons = customer.getPersons();

                for (Person person: persons ) {
                    person.setCustomer(customer);
                }

                float roomPrice = customer.getRoom().getPrice();

                log.debug("roomPrice bill is: "+ roomPrice);

                float bill  = ( roomPrice * customerBookingDates.size() );

                customer.setBill(bill);
                log.debug("customer bill is: "+ bill);

                return customerRepo.save(customer);
            }
            throw new RoomIsBookedException("this room is already booked for this days");
        }

}
