package com.hotels.example.repositories;

import com.hotels.example.model.Person;
import org.springframework.data.repository.CrudRepository;



public interface PersonRepo extends CrudRepository<Person,Integer> {

}
