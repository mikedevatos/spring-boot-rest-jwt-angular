package com.hotels.example.model;


import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
public class CurrentUser implements Serializable {

    private String username;
    private String role;

    @Override
    public String toString() {
        return "CurrentUser{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
