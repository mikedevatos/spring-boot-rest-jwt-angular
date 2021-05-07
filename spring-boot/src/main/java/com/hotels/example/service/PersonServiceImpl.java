package com.hotels.example.service;
import com.hotels.example.errors.RoomCapacitySurpassedException;
import com.hotels.example.model.Customer;
import com.hotels.example.model.Person;
import com.hotels.example.repositories.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;

@Service
public class PersonServiceImpl {

    private PersonRepo personRepo;
    Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Caching(
            evict = {
            @CacheEvict(value="customersDTO",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result !=null")
    })
    @Transactional(rollbackFor = RoomCapacitySurpassedException.class)
    public  Person create(Person person, Customer custo)  throws RoomCapacitySurpassedException {
        if( custo.getPersons().size() >=  custo.getRoom().getRoomCapacity() )
           throw new RoomCapacitySurpassedException("room capacity has been reached ");

           return personRepo.save(person);
    }

    @Transactional
    @Caching( evict = {
            @CacheEvict(value="customersDTO",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result != null")
    })
    public Person update(Person p){
        return personRepo.save(p);
    }


public Person findBy_Id(Integer id) {
    Person person = personRepo.findById(id)
                              .orElseThrow(EntityNotFoundException::new);

    return person;
}


@Transactional
@Caching( evict = {
        @CacheEvict(value="customersDTO",allEntries = true,cacheManager = "caffeineCacheManager",condition = "#result == true")
})
 public boolean delete (Integer id){
         personRepo.deleteById(id);
         return true;
 }


}
