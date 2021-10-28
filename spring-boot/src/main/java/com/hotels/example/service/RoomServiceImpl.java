package com.hotels.example.service;


import com.fasterxml.jackson.annotation.JsonView;
import com.hotels.example.errors.RoomCapacitySurpassedException;
import com.hotels.example.errors.RoomIsBookedException;
import com.hotels.example.model.Booking;
import com.hotels.example.model.Customer;
import com.hotels.example.model.Room;
import com.hotels.example.model.Views;
import com.hotels.example.repositories.RoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class RoomServiceImpl {

    private RoomRepo roomRepo;

    @Autowired
    public RoomServiceImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @Cacheable(cacheNames="roomBookings",key="#id",cacheManager = "caffeineCacheManager")
    public List<Booking> allRoomBookings(Integer id ){

        Optional<Room> room = findById(id);
        List<Booking> bookings =new ArrayList<>();

        if   ( room.isPresent()  ) {
          bookings =      !room.get().getCustomers().isEmpty()                   ?
                           room.get().getCustomers()
                          .stream()
                          .map (Customer::getBooking)
                          .collect(Collectors.toList()) : new ArrayList<>();
         }
         return bookings;
    }

    public List<Room> findAll() {
        List<Room> rooms= roomRepo.findAll();
        return rooms;
    }

    public Optional<Room> findById(Integer id) {
        Optional<Room> room = roomRepo.findById(id);
        return room;
    }

    public Room findBy_Id(Integer id){
     Room room = roomRepo.findById(id).orElseThrow(EntityNotFoundException::new);
     return room;
    }


    @Transactional
    @Caching( evict = {
            @CacheEvict(value="customers",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result != null"),
            @CacheEvict(value="roomBookings",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result != null")
    })
    public  Room create(Room room)  {
        return  roomRepo.save(room);
    }


    /** updating room **/
    @Transactional(rollbackFor = RoomCapacitySurpassedException.class)
    @Caching( evict = {
            @CacheEvict(value="roomBookings",key = "#room.getId()",cacheManager = "caffeineCacheManager",condition = "#result != null"),
            @CacheEvict(value="customers",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result != null"),
    })
    public Room update(Room room){

        Room initialState = roomRepo.findById( room.getId() )
                                   .orElseThrow(EntityNotFoundException::new);

        if( room.getRoomCapacity() < initialState.getRoomCapacity() ){

        boolean  isRoomCapacitySurpassed  = !initialState.getCustomers().isEmpty() && initialState.getCustomers()
                                                         .stream()
                                                         .anyMatch(c -> c.getPersons().size() > room.getRoomCapacity());

        if(isRoomCapacitySurpassed)
            throw new RoomCapacitySurpassedException("cant update room  capacity persons surpassed it");

        }
       return  roomRepo.save(room);
    }

    /** deleting room **/
     @Transactional
     @Caching( evict = {
             @CacheEvict(value="roomBookings",key = "#roomId",cacheManager = "caffeineCacheManager",condition = "#result == true"),
             @CacheEvict(value="customers",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result != null"),
     })
    public boolean deleteById(Integer roomId){
         Room room = findBy_Id(roomId);
          if( !room.getCustomers().isEmpty() )
              throw  new RoomIsBookedException("can't delete not empty room");

         roomRepo.deleteById(roomId);
        return true;
     }


    public long count(){
        return  roomRepo.count();
    }

    @JsonView(Views.EntityOnly.class)
    public List<Room> findAllbyPage(int page, int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Room> rooms = roomRepo.findAll(paging);
        List<Room> listRooms = rooms.getContent();
        return listRooms;
    }

}
