package com.hotels.example.repositories;

import com.hotels.example.model.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepo extends JpaRepository<Roles,Long> {

  Roles findByType(String type);
  @Query(
            value = "select count(*) from roles  where roles.type_role='EMPLOYEE'",
            nativeQuery = true)
    long countEmployees();

}
