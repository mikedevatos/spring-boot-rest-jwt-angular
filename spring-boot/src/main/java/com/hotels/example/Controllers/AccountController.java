package com.hotels.example.Controllers;

import com.hotels.example.model.CurrentUser;

import com.hotels.example.service.CustomerServiceImpl;
import com.hotels.example.service.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(value="Account Management")
@Validated
@RestController
@RequestMapping(value = "/api")

public class AccountController {
    Logger log = LoggerFactory.getLogger(AccountController.class);

    CustomerServiceImpl customerService;
    UserDetailsServiceImpl userService;

    @Autowired
    public AccountController(UserDetailsServiceImpl userService, CustomerServiceImpl customerService) {
        this.userService= userService;
        this.customerService=customerService;
    }


    @ApiOperation(value="current user info")
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<CurrentUser>  getCurrentUserInfo() {
        String username=null;

        Authentication authe =  SecurityContextHolder.getContext().getAuthentication();

        log.debug("principal username is {} ",authe.getPrincipal().toString());




        if(!(authe  instanceof AnonymousAuthenticationToken)) {
            String roles = authe.getAuthorities().toString();
            username = authe.getPrincipal().toString();

            log.debug("username is {} ", username);

            log.debug("User has Role: " + roles.substring(6,roles.length()-1));


            CurrentUser currentUser = new CurrentUser();
            currentUser.setUsername(username);
            currentUser.setRole(roles.substring(6 , (roles.length()-1) ) );


                  return new ResponseEntity(currentUser, HttpStatus.OK);
        }
        else{


           return new ResponseEntity( HttpStatus.UNAUTHORIZED);

        }
    }


}
