package com.hotels.example.repositories;

import com.hotels.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;




public interface CustomerRepo extends JpaRepository<Customer,Integer> {

    Customer   findByBooking_Id(Integer idBooking);

}
