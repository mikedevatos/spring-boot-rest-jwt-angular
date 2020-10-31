package com.hotels.example.repositories;


import com.hotels.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;



public interface EmployeePageRepo extends PagingAndSortingRepository<User,Long> {




    Page<User> findByRoles_Type_Like(String employee,Pageable pageable);


}
