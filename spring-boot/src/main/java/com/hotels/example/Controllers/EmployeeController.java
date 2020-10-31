package com.hotels.example.Controllers;



import com.hotels.example.model.*;
import com.hotels.example.repositories.EmployeeRepo;
import com.hotels.example.repositories.RolesRepo;
import com.hotels.example.service.EmployeePageServiceImpl;
import com.hotels.example.service.EmployeeServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
@Validated
public class EmployeeController {
    Logger log = LoggerFactory.getLogger(EmployeeController.class);


    private EmployeeRepo  employeeRepo;
    private RolesRepo rolesRepo;
    private EmployeeServiceImpl employeeService;
    private EmployeePageServiceImpl employeePageService;
    private PasswordEncoder passwordEncoder ;



    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService,
                              EmployeeRepo  employeeRepo,RolesRepo rolesRepo,
                              EmployeePageServiceImpl employeePageService,
                              PasswordEncoder passwordEncoder

                              )
    {
        this.employeeService = employeeService;
        this.employeeRepo=employeeRepo;
        this.rolesRepo = rolesRepo;
        this.employeePageService=employeePageService;
        this.passwordEncoder =passwordEncoder;

    }


    @ApiOperation(value = "list paged employees",response = EmployeeController.class)
    @RequestMapping(value = "/employee/{page}/{size}", method = RequestMethod.GET)
    public ResponseEntity<EmployeesDTO> getPageCustomers(@PathVariable int page, @PathVariable int size) {

        long total = employeeService.count();

        log.debug("total employees records is "+total);

        List<User> empl = employeePageService.findPagedEmployees(page,size);
        List<EmployeeDTO> employeeDtoList = new ArrayList<>();

        empl.stream().forEach((e) -> {
            EmployeeDTO empDto = new EmployeeDTO();
             empDto.setId(e.getId());
            empDto.setUsername(e.getUsername());
            empDto.setFirstName(e.getFirstName());
             empDto.setLastName(e.getLastName());
            employeeDtoList.add((empDto));
        });

        EmployeesDTO employeesDTO =new EmployeesDTO();

        employeesDTO.setEmployees(employeeDtoList);

        employeesDTO.setCount(total);

        log.debug("showing   employees page  "+page +"  and size  " + size);

        return new ResponseEntity<>(employeesDTO, HttpStatus.OK);

    }
    
    @ApiOperation(value = "delete employee",response = ResponseEntity.class)
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id){


        Optional<User> user = employeeRepo.findById(id);
        if(user.isPresent()){
            employeeService.deleteEmployee(id);
            log.debug("delete user with id {}", id);

            return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.OK);
        }


        return  new  ResponseEntity<String>(String.valueOf(id), HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "create employee",response = EmployeeController.class)
 @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ResponseEntity<String> createEmployee(@Valid @RequestBody User user){

    if (user.getId() != null) {

        return  new  ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }


     Roles role = rolesRepo.findByType("EMPLOYEE");
     Set<Roles> roles = new HashSet<>();
     roles.add(role);
     user.setRoles(roles);
         user.setPassword(passwordEncoder.encode(user.getPassword()));
        employeeService.saveEmployee(user);

     return  new  ResponseEntity<>(HttpStatus.CREATED);

    }
    @ApiOperation(value = "update employee",response = EmployeeController.class)
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employee) {


        if( employeeService.findById(employee.getId()).isPresent()){
             User u =   employeeService.findById(employee.getId()).get();
            if(employee.getPassword().isPresent()) {
                u.setUsername(employee.getUsername());
                u.setLastName(employee.getLastName());
                u.setFirstName(employee.getFirstName());
                u.setPassword(passwordEncoder.encode(employee.getPassword().get()  )  ) ;

                 employeeService.saveEmployee(u);

              return new  ResponseEntity<>( employee,HttpStatus.ACCEPTED);

            }


             u.setFirstName(employee.getFirstName());
             u.setLastName(employee.getLastName());
             u.setUsername(employee.getUsername());
            employeeService.saveEmployee(u);
            log.debug("update employee with id {}",employee.getId());
            return new  ResponseEntity<>( employee,HttpStatus.ACCEPTED);
        }



        return  new  ResponseEntity<>(employee, HttpStatus.BAD_REQUEST);




    }






}
