package com.hotels.example.service;


import com.hotels.example.model.Room;
import com.hotels.example.repositories.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RoomServiceImpl {




    private RoomRepo roomRepo;

    @Autowired
    public RoomServiceImpl(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;

    }




    public List<Room> findAll() {
        List<Room> rooms= roomRepo.findAll();
        return rooms;
    }







}
