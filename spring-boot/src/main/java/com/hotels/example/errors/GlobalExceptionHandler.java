package com.hotels.example.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RoomIsBookedException.class,
            NoChangesHaveBeenMadeToBooking.class,
            RoomCapacitySurpassedException.class})
    public ResponseEntity<String> generateGeneralResponseException(Exception ex) {

        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleUnknownException( RuntimeException exception) {

        exception.printStackTrace();
        return new ResponseEntity<String>("something went wrong", HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();

        FieldError fieldError  =  result.getFieldErrors().stream()
                .filter(e -> !e.getField().isEmpty())
                .findFirst().get();

        ErrorDTO error = new ErrorDTO();
       error.setMessage( "error "+fieldError.getDefaultMessage()+ " for field " +fieldError.getField() );
       error.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error ,error.getStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(EntityNotFoundException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setMessage("error this entity doesnt exist ");
        return new ResponseEntity<>(error, error.getStatus());
    }
}
