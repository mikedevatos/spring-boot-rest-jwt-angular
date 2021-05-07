package com.hotels.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
public class LoginUser implements Serializable {


    @Size(min=4,max = 64)
    private String username;

    @Size(min=4,max = 64)
    private String password;

}
