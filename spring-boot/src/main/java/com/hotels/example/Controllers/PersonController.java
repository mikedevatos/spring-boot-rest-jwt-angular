package com.hotels.example.Controllers;

import com.hotels.example.dto.PersonUpdateDTO;
import com.hotels.example.model.Customer;
import com.hotels.example.model.Person;
import com.hotels.example.repositories.CustomerRepo;
import com.hotels.example.repositories.PersonRepo;
import com.hotels.example.service.PersonServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class PersonController {

    private PersonRepo personRepo;

    private PersonServiceImpl personService;

    private CustomerRepo  customerRepo;

    @Autowired
    public PersonController(PersonServiceImpl personService,
                            PersonRepo personRepo,
                            CustomerRepo  customerRepo)
                            {
        this.personService = personService;
        this.personRepo = personRepo;
        this.customerRepo=customerRepo;
    }

    Logger log = LoggerFactory.getLogger(PersonController.class);

    @RequestMapping(value = "/person", method = RequestMethod.PUT)

    public ResponseEntity<Person> update(@Valid @RequestBody Person p) {

        if(personRepo.findById(p.getId()).isPresent()){

            Person person  = personService.findBy_Id( p.getId() );

            person.setEmail(p.getEmail());
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());

            personService.update(person);

            log.debug("update person with id {}",person.getId());
            return new  ResponseEntity<>( p, HttpStatus.ACCEPTED);
        }
        return new  ResponseEntity<>(p, HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity<PersonUpdateDTO> create(@Valid @RequestBody PersonUpdateDTO personDTO)  {

      if(personDTO.getId() == null && this.customerRepo.existsById(personDTO.getCustomerId() ) ){

        Customer custo = customerRepo.findById(personDTO.getCustomerId())
                                      .orElseThrow(RuntimeException::new);

            Person person = new Person();
            person.setCustomer(custo);
            person.setEmail(personDTO.getEmail());
            person.setFirstName(personDTO.getFirstName());
            person.setLastName(personDTO.getLastName());

            personService.create(person, custo);

            return  new  ResponseEntity<>(personDTO, HttpStatus.OK);
      }

            return  new  ResponseEntity<>(personDTO, HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id){

        Optional<Person> person = personRepo.findById(id);

        if( person.isPresent() ){

            log.debug("delete person with id {}", id);
            personService.delete(id);

            return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.OK);
        }
        return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.NOT_FOUND);
    }


}
