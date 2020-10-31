package com.hotels.example.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeesDTO {

    List<EmployeeDTO> employees = new ArrayList<>();
    long count ;

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
