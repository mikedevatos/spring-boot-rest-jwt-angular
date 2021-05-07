package com.hotels.example.Controllers;

import com.hotels.example.model.Booking;
import com.hotels.example.model.Customer;
import com.hotels.example.model.Room;
import com.hotels.example.repositories.BookingRepo;
import com.hotels.example.repositories.CustomerRepo;
import com.hotels.example.service.BookingServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@Validated
public class BookingController {

    Logger log = LoggerFactory.getLogger(BookingController.class);


    private BookingServiceImpl bookingsService;
    private BookingRepo bookingRepo;
    private CustomerRepo customerRepo;

    @Autowired
    public BookingController(BookingServiceImpl bookingsService,
                             BookingRepo bookingRepo,
                             CustomerRepo customerRepo) {
        this.bookingsService = bookingsService;
        this.bookingRepo = bookingRepo;
        this.customerRepo =customerRepo;
    }

    @RequestMapping(value = "/booking", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<Booking> update(@Valid @RequestBody Booking booking) {

        log.debug("booking controller updating BOOKING : " +booking.toString()  );

        if ( bookingRepo.existsById( booking.getId() ) ) {

            Customer custo = customerRepo.findByBooking_Id( booking.getId() );

            Room room =custo.getRoom();

            bookingsService.update(booking,custo,room);

            return new ResponseEntity<>(booking, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(booking, HttpStatus.NOT_FOUND);

    }
}






