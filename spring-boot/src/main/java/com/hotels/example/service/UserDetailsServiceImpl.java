package com.hotels.example.service;

import com.hotels.example.model.User;
import com.hotels.example.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepository) {
        this.userRepo = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = this.userRepo.findByUsername(s);

        if (user.isPresent()){
            return user.get();
        }else{


            throw new UsernameNotFoundException(String.format("Username not found"));
        }

    }

   
}
