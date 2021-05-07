package com.hotels.example.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
public class PersonUpdateDTO implements Serializable {

    private Integer id;

    @Size(min=4,max = 64)
    private String firstName;

    @Size(min=4,max = 64)
    private String lastName;

    @Size(min=4,max = 128)
    @NotNull
    private String email;

    @NotNull
    private Integer customerId;

}
