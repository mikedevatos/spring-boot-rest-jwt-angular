package com.hotels.example.repositories;


import com.hotels.example.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room,Long> {


    @Override
    List<Room> findAll();


}


