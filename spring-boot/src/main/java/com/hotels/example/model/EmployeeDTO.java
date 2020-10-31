package com.hotels.example.model;


import org.hibernate.validator.constraints.*;
import java.util.Optional;


public class EmployeeDTO {

    private Long id;

    @Length(min=4,max = 64)
    private String username;

    @Length(min=4,max = 64)
    private String firstName;


    @Length(min=4,max = 64)
    private String lastName;


    private Optional<@Length(min=4,max = 64,message = "password must be between 15 and 64 " ) String> password =Optional.empty();





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = Optional.of(pass);
    }


}
