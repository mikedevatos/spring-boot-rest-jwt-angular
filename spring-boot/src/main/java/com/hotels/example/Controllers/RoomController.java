package com.hotels.example.Controllers;


import com.hotels.example.model.Customer;
import com.hotels.example.model.Room;
import com.hotels.example.service.CustomerServiceImpl;
import com.hotels.example.service.RoomServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")

public class RoomController {

    Logger log = LoggerFactory.getLogger(RoomController.class);


    private CustomerServiceImpl customerService;
    private RoomServiceImpl roomService;

    @Autowired
    public RoomController(CustomerServiceImpl customerService,RoomServiceImpl roomService) {
        this.customerService = customerService;
        this.roomService=roomService;
    }



    @ApiOperation(value = "list all rooms",response = ResponseEntity.class)
    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> getCustomers() {

        List<Room> rooms = roomService.findAll();
        log.debug("showing rooms");

        return new ResponseEntity(rooms, HttpStatus.OK);

    }


    @ApiOperation(value = "delete room",response = ResponseEntity.class)
    @RequestMapping(value = "/room/{idCusto}/{idRoom}", method = RequestMethod.DELETE)

    public ResponseEntity<String> delete(@PathVariable Long idCusto,@PathVariable Long idRoom){

        Optional<Customer> customer =  customerService.findById(idCusto);
     if (customer.isPresent()) {
       if (  customer.get().getRooms().removeIf(room -> room.getId()==idRoom)) {

               Customer custo = customer.get();
               customerService.save(custo);

                log.debug("delete room with id {}", idRoom,"of customer with id {}",idCusto);


               return new ResponseEntity<String>(String.valueOf(idRoom), HttpStatus.ACCEPTED);
           }
       }


        return  new  ResponseEntity<String>(String.valueOf(idCusto)+"Not found room  with room id "+idRoom +"of  customer with id " + idCusto,  HttpStatus.NOT_FOUND);
    }

}
