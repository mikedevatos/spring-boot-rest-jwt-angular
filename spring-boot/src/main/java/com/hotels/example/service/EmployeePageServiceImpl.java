package com.hotels.example.service;

import com.hotels.example.model.User;
import com.hotels.example.repositories.EmployeePageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;




@Service
public class EmployeePageServiceImpl {
    private    EmployeePageRepo employeePageRepo;
    private  final String employeeRole="EMPLOYEE";


    @Autowired
    public EmployeePageServiceImpl(EmployeePageRepo employeePageRepo) {
        this.employeePageRepo = employeePageRepo;
    }




    public List<User> findPagedEmployees(int page,int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<User> pagedEmpl = employeePageRepo.findByRoles_Type_Like(employeeRole,paging);
        List<User> emp = pagedEmpl.getContent();

        return  emp;

    }



}
