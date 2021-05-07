package com.hotels.example.repositories;

import com.hotels.example.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepo extends CrudRepository<Booking,Integer> {
}
