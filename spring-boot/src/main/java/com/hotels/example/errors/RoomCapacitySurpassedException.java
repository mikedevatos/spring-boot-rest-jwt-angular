package com.hotels.example.errors;

public class RoomCapacitySurpassedException extends  RuntimeException {
    public RoomCapacitySurpassedException(String message){
       super(message);
    }

}
