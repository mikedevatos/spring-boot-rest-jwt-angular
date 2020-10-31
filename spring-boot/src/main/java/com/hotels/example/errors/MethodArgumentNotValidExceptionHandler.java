package com.hotels.example.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

      FieldError  fieldError  =  result.getFieldErrors().stream()
                .filter(e -> !e.getField().isEmpty())
                .findFirst().get();

        Error errors = new Error();
        errors.setFieldName(fieldError.getField());
        errors.setObjectName(Error.employeeDto_To_employee(fieldError.getObjectName()));
        errors.setMessage(fieldError.getDefaultMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

         return errors;

    }


    static class Error {

        private int status;
        private  String message;
        private String fieldName;
        private String objectName;



        public static String employeeDto_To_employee(String employeeDto){
            return    employeeDto.substring(0,employeeDto.length() - 3);
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }



        public void setStatus(int status) {
            this.status = status;
        }

        public void setMessage(String message) {
            this.message = message;
        }


    }

}






