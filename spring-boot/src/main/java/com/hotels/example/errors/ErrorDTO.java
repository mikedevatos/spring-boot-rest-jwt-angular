package com.hotels.example.errors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorDTO {
    public HttpStatus status;
    public String message;
}
