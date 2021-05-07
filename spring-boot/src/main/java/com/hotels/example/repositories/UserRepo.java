package com.hotels.example.repositories;

import com.hotels.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
     Optional<User> findByUsername(String username);
}
