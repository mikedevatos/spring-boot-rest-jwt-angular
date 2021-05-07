package com.hotels.example.repositories;

import com.hotels.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepo extends JpaRepository<User,Integer> {


    Page<User> findByRole_Type_Like(String employee, Pageable pageable);


}
