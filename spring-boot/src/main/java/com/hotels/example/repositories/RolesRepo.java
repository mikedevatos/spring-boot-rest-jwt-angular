package com.hotels.example.repositories;

import com.hotels.example.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles,Long> {
  Roles findByType(String type);

}
