package com.hotels.example.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Optional;

public class EmployeeDTO implements Serializable {

    private Integer id;

    @Size(min=4,max = 64)
    private String username;

    @Size(min=4,max = 64)
    private String firstName;


    @Size(min=4,max = 64)
    private String lastName;

    private Optional<@Size(min=4,max = 64,message = "password must be between 4 and 64 " ) String> password =Optional.empty();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
