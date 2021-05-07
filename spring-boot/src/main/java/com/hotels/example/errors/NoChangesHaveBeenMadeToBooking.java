package com.hotels.example.errors;

public class NoChangesHaveBeenMadeToBooking  extends  RuntimeException{

    public NoChangesHaveBeenMadeToBooking(String message){
        super(message);
    }
}
