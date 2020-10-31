package com.hotels.example.Controllers;


import com.hotels.example.model.Customer;
import com.hotels.example.model.CustomersDTO;
import com.hotels.example.service.CustomerPageServiceImpl;
import com.hotels.example.service.CustomerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;




@RestController
@Api(value="Customer Management System")
@RequestMapping(value = "/api")
public class CustomerController {
    Logger log =LoggerFactory.getLogger(CustomerController.class);

    private CustomerServiceImpl customerService;
     private CustomerPageServiceImpl  customerPageService;


    @Autowired
    public CustomerController(CustomerServiceImpl customerService,CustomerPageServiceImpl customerPageService) {
        this.customerService = customerService;
        this.customerPageService=customerPageService;
    }


    @ApiOperation(value = "list customers",response = ResponseEntity.class)
    @RequestMapping(value = "/customer/{page}/{size}", method = RequestMethod.GET)

    public ResponseEntity<CustomersDTO> getPageCustomers(@PathVariable int page, @PathVariable int size) {

        long total = customerService.count();

        log.debug("total customer records  is "+total);

        List<Customer> customers = customerPageService.findAllbyPage(page,size);
        CustomersDTO customDTO=new CustomersDTO();
        customDTO.setCustomer(customers);
        customDTO.setCount(total);

        log.debug("showing   customers page  "+page +"  and size  " + size);

        return new ResponseEntity<>(customDTO, HttpStatus.OK);

    }



    // it is called also when adding new room or update it
    @ApiOperation(value = "update customer",response = ResponseEntity.class)
    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    public ResponseEntity<Customer> update(@Valid @RequestBody Customer customer) {

        if(customerService.findById(customer.getId()).isPresent()){

            customerService.save(customer);
            log.debug("update customer with id {}",customer.getId());
            return new  ResponseEntity<>( customer,HttpStatus.ACCEPTED);
        }


        return new  ResponseEntity<>(customer, HttpStatus.NOT_FOUND);


    }




   @ApiOperation(value = "adding customer",response = ResponseEntity.class)
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer){

       if (customer.getId() != null) {

           return  new  ResponseEntity<>(customer, HttpStatus.NOT_ACCEPTABLE);

       }

       customerService.save(customer);

        return  new  ResponseEntity<>( customer , HttpStatus.CREATED);


   }






   @ApiOperation(value = "delete customer",response = ResponseEntity.class)
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
//    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> delete(@PathVariable Long id){

         Optional<Customer> customer = customerService.findById(id);
               if(customer.isPresent()){
                   log.debug("delete customer with id {}", id);
                   customerService.deleteById(id);
                   return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.OK);
               }


        return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.NOT_FOUND);
    }








}


