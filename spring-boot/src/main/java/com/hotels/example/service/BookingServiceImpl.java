package com.hotels.example.service;

import com.hotels.example.errors.NoChangesHaveBeenMadeToBooking;
import com.hotels.example.errors.RoomIsBookedException;
import com.hotels.example.errors.StartDateAfterEndDateException;
import com.hotels.example.model.Booking;
import com.hotels.example.model.Customer;
import com.hotels.example.model.Room;
import com.hotels.example.repositories.BookingRepo;
import com.hotels.example.repositories.CustomerRepo;
import com.hotels.example.util.DatesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl {

   Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

   private BookingRepo bookingRepo ;
   private CustomerRepo customerRepo;

   @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo, CustomerRepo customerRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
    }

    @Caching(evict = {
              @CacheEvict(value="customersDTO",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result !=null"),
              @CacheEvict(cacheNames="roomBookings",key="#room.getId()",cacheManager = "caffeineCacheManager", condition = "#result !=null")
             } )
    @Transactional(rollbackFor = {RoomIsBookedException.class,StartDateAfterEndDateException.class,NoChangesHaveBeenMadeToBooking.class})
    public Booking update (Booking booking, Customer custo, Room room )  throws RoomIsBookedException, StartDateAfterEndDateException, NoChangesHaveBeenMadeToBooking {

        final LocalDate startDate = booking.getStartBooking().toLocalDate();
        final LocalDate endDate = booking.getEndBooking().toLocalDate();

        if (   custo.getBooking().getStartBooking().toLocalDate().equals(startDate) &&
                custo.getBooking().getEndBooking().toLocalDate().equals(endDate)       )
            throw new NoChangesHaveBeenMadeToBooking("booking dates  have not been modified error updating");

        log.debug(" booking updating start date is :" + startDate + " endate :" + endDate);

        if ( startDate.isAfter(endDate) )
            throw new StartDateAfterEndDateException("start date is after end date error");

        /** get bookings for updatable room for comparison with customer booking dates  **/
        List<Booking> bookings = !room.getCustomers().isEmpty()    ?

                                  room.getCustomers()
                                 .stream()
                                 .map(Customer::getBooking)

                                 /**dont include current booking for comparison **/
                                 .filter(b -> b.getId() != booking.getId())

                                 .filter(b ->
                                         !( endDate.isBefore(b.getStartBooking().toLocalDate())  ||
                                            startDate.isAfter(b.getEndBooking().toLocalDate()       )))

                                 .collect(Collectors.toList()) : new ArrayList<>();

        /**get dates in range for customer if bookings not empty **/
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

             if( isRoomBooked.get() )
                 throw new RoomIsBookedException("this date is booked from other customer");

        float roomPrice = custo.getRoom().getPrice();
        log.debug(" bill is: "+ roomPrice);
        float bill  = ( roomPrice * customerBookingDates.size() );
        custo.setBill(bill);
        log.debug("saving booking " + "with start day " +booking.getStartBooking().toString() +"with end day" + booking.getEndBooking().toString() );
        return     bookingRepo.save(booking);
    }


}
