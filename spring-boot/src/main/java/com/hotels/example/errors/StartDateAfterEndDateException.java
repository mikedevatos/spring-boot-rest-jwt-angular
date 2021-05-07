package com.hotels.example.errors;

public class StartDateAfterEndDateException extends  Exception {
    public StartDateAfterEndDateException(String message){
        super(message);
    }


}
