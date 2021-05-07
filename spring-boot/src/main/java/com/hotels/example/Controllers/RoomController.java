package com.hotels.example.Controllers;


import com.fasterxml.jackson.annotation.JsonView;
import com.hotels.example.dto.RoomsDTO;
import com.hotels.example.model.*;
import com.hotels.example.repositories.RoomRepo;
import com.hotels.example.service.RoomServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class RoomController {

    Logger log = LoggerFactory.getLogger(RoomController.class);

    private RoomServiceImpl roomService;
    private RoomRepo       roomRepo;

    @Autowired
    public RoomController(
                          RoomServiceImpl roomService,
                          RoomRepo roomRepo
                          ) {
        this.roomService=roomService;
        this.roomRepo=roomRepo;
    }


    @RequestMapping(value = "/room", method = RequestMethod.GET)
    @JsonView(Views.EntityOnly.class)
    @Cacheable(cacheNames="rooms",cacheManager = "caffeineCacheManager")
    public ResponseEntity<List<Room>> getRooms() {

        List<Room> rooms = roomService.findAll();
        log.debug("showing rooms");

        return new ResponseEntity(rooms, HttpStatus.OK);
    }


    @RequestMapping(value = "/room/{page}/{size}", method = RequestMethod.GET)
    @JsonView(Views.EntityOnly.class)
    @Cacheable(cacheNames="roomsDTO",key="#page",cacheManager = "caffeineCacheManager")
    public ResponseEntity<RoomsDTO> getPageRooms(@PathVariable Integer page, @PathVariable int size) {

        long total = roomService.count();

        List<Room> rooms = roomService.findAllbyPage(page,size);

        RoomsDTO roomsDTO=new RoomsDTO();
        roomsDTO.setRooms(rooms);
        roomsDTO.setCount(total);

        log.debug("showing   rooms page  "+page +"  and size  " + size);
        return new ResponseEntity<>(roomsDTO, HttpStatus.OK);
    }


    @RequestMapping(value = "/room/{idRoom}", method = RequestMethod.GET)
    @JsonView(Views.ResponseView.class)
    @Cacheable(cacheNames="roomBookings",key="#idRoom",cacheManager = "caffeineCacheManager")
    public ResponseEntity<List<Booking>> getRoomBookings(@PathVariable Integer idRoom) {

        List<Booking> bookings = roomService.allRoomBookings(idRoom);

        log.debug("showing booking dates for room");

        return new ResponseEntity(bookings, HttpStatus.OK);

    }

    @RequestMapping(value = "/room", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<Room> update(@Valid @RequestBody Room room) {

        if( roomRepo.existsById(room.getId() ) ) {

                roomService.update(room);

            log.debug("update room with id {}",room.getId());
            return new  ResponseEntity<>( room, HttpStatus.ACCEPTED);
        }

        return new  ResponseEntity<>(room, HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public ResponseEntity<Room> create(@Valid @RequestBody Room room)  {
        if(room.getId() == null){
                roomService.create(room);

            return new  ResponseEntity<>( room, HttpStatus.CREATED);
        }
        return  new  ResponseEntity<>(room, HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/room/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id){


        if(roomRepo.existsById( id  ) ){

            log.debug("delete room with id {}", id);
            roomService.deleteById(id);
            return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.OK);
        }
        return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.NOT_FOUND);
    }
}
