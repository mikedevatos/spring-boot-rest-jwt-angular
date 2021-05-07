package com.hotels.example.repositories;


import com.hotels.example.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room,Integer> {

}


