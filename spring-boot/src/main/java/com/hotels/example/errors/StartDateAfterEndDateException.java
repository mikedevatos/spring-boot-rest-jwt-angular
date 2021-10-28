package com.hotels.example.errors;

public class StartDateAfterEndDateException extends  RuntimeException {

    public StartDateAfterEndDateException(String message){
        super(message);
    }


}
