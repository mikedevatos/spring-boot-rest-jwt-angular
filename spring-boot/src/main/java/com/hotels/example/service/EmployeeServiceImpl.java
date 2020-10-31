package com.hotels.example.service;

import com.hotels.example.model.User;
import com.hotels.example.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class EmployeeServiceImpl  {

     private  final String employeeRole="EMPLOYEE";

     private EmployeeRepo employeeRepo;

     @Autowired
     public EmployeeServiceImpl(EmployeeRepo employeeRepo){
         this.employeeRepo=employeeRepo;
     }



@Transactional
public void deleteEmployee(Long id){
         employeeRepo.deleteById(id);
    }


   public long count(){
         return  employeeRepo.countEmployess();
   }




    public Optional<User> findById(Long id) {
        Optional<User> emp = employeeRepo.findById(id);
        return emp;
    }


 public User saveEmployee(User user){
     return    employeeRepo.save(user);
 }


}
