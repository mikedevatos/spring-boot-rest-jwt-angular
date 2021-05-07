package com.hotels.example.errors;

public class RoomIsBookedException  extends  RuntimeException{

    public RoomIsBookedException(String message){
        super(message);
    }
}
