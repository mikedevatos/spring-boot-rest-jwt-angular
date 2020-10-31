package com.hotels.example.service;

import com.hotels.example.model.Customer;
import com.hotels.example.repositories.CustomerPageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CustomerPageServiceImpl
{

    CustomerPageRepo customerPageRepo;


      @Autowired
    public CustomerPageServiceImpl(CustomerPageRepo customerPageRepo) {
        this.customerPageRepo = customerPageRepo;
    }


    public List<Customer> findAllbyPage(int page,int size){
        Pageable paging = PageRequest.of(page, size);
        Page<Customer> pagedCusto = customerPageRepo.findAll(paging);
         List<Customer> custo = pagedCusto.getContent();

          return custo;
    }

}
