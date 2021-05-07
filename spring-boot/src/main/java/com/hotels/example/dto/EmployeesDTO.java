package com.hotels.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class EmployeesDTO implements Serializable {

    List<EmployeeDTO> employees = new ArrayList<>();
    long count ;

}
