package com.hotels.example.repositories;

import com.hotels.example.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CustomerPageRepo  extends PagingAndSortingRepository<Customer,Long> {

           Page<Customer> findAll(Pageable pageable);
}
