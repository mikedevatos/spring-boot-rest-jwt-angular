package com.hotels.example.service;

import com.hotels.example.model.Customer;
import com.hotels.example.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl{
    private CustomerRepo customerRepo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }




    public Optional<Customer> findById(Long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        return customer;
    }


    public long count(){
      return  customerRepo.count();
    }

    @Transactional
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    public <S extends Customer> S save(S s) {
        return customerRepo.save(s);
    }

}
