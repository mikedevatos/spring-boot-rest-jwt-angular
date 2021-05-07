package com.hotels.example.Controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.hotels.example.dto.CustomerUpdateDTO;
import com.hotels.example.dto.CustomersDTO;
import com.hotels.example.model.*;
import com.hotels.example.repositories.CustomerRepo;
import com.hotels.example.repositories.RoomRepo;
import com.hotels.example.service.CustomerServiceImpl;
import com.hotels.example.service.RoomServiceImpl;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
public class CustomerController {
    Logger log =LoggerFactory.getLogger(CustomerController.class);

     private CustomerServiceImpl customerService;
     private CustomerRepo customerRepo;
     private RoomServiceImpl  roomService;
     private RoomRepo roomRepo;


    @Autowired
    public CustomerController(CustomerServiceImpl customerService,
                              CustomerRepo customerRepo,
                              RoomServiceImpl roomService,
                              RoomRepo roomRepo) {
        this.customerService = customerService;
        this.customerRepo = customerRepo;
        this.roomService = roomService;
        this.roomRepo=roomRepo;
    }

    /**get**/
    @RequestMapping(value = "/customer/{page}/{size}", method = RequestMethod.GET)
    @Cacheable(cacheNames="customersDTO",key="#page",cacheManager = "caffeineCacheManager")
    @JsonView(Views.ResponseView.class)
    public ResponseEntity<CustomersDTO> getPageCustomers(@PathVariable Integer page, @PathVariable int size) {

        long total = customerService.count();

        List<Customer> customers = customerService.findAllbyPage(page,size);
        CustomersDTO customDTO=new CustomersDTO();
        customDTO.setCustomer(customers);
        customDTO.setCount(total);
        log.debug("showing   customers page  "+page +"  and size  " + size);
        return new ResponseEntity<>(customDTO, HttpStatus.OK);
    }

    /**put**/
    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<CustomerUpdateDTO> update(@Valid @RequestBody CustomerUpdateDTO custoDTO) {

        if (this.customerRepo.existsById( custoDTO.getId() ) ){

            Customer custo  = customerService.findBy_Id( custoDTO.getId() );

            Optional<Room> optionalRoomReplacing = custoDTO.getRoom();

            if(optionalRoomReplacing.isPresent() && roomRepo.existsById( optionalRoomReplacing.get().getId() ) ) {

                      Room replacingRoom = roomRepo.getOne( optionalRoomReplacing.get().getId() );
                      Room initialRoom =custo.getRoom();

                      if( !( initialRoom.getId() == replacingRoom.getId() ) )
                      {
                          customerService.replaceRoom( custo, replacingRoom );
                          log.debug("update customer with id {}", custoDTO.getId());
                          return new ResponseEntity<>(custoDTO, HttpStatus.ACCEPTED);
                       }
                      else { throw new RuntimeException("room hasn't been changed"); }

                      }

            boolean isPresentAccountType=false;
            boolean isPresentBill=false;

            if( custoDTO.getBill().isPresent() ){
                float bill = custoDTO.getBill().get();
                custo.setBill( bill );
                log.debug("updating customer bill is present", custoDTO.getId() );
                isPresentBill=true;
            }

            if( custoDTO.getAccountType().isPresent() ){
                custo.setAccountType( custoDTO.getAccountType().get() );
                log.debug("updating customer  accountType is present", custoDTO.getId() );
                isPresentAccountType=true;
            }

          if( isPresentBill || isPresentAccountType  ) {
                  customerService.update(custo);
           return new ResponseEntity<>(custoDTO, HttpStatus.ACCEPTED);
          }
        }
        return new  ResponseEntity<>(custoDTO, HttpStatus.NOT_FOUND);
    }

/**post**/
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @SneakyThrows
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer){

       if (customer.getId() != null)
           return  new  ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);

        customerService.create(customer);

        return  new  ResponseEntity<>( customer , HttpStatus.CREATED);
   }


/** delete**/
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Integer id){

         Optional<Customer> customer = customerRepo.findById(id);

          if(  customer.isPresent()  ) {
               log.debug("delete customer with id {}", id);
               customerService.delete(id);
              return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.OK);
          }

        return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.NOT_FOUND);
    }



}


