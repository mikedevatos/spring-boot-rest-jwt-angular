package com.hotels.example.repositories;

import com.hotels.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



@Transactional
public interface CustomerRepo extends JpaRepository<Customer,Long> {


    @Override
    <S extends Customer> S save(S entity);

    @Override
    Optional<Customer> findById(Long aLong);

    @Override
   void deleteById(Long id);

}
