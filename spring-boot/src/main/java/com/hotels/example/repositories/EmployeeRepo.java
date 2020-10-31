package com.hotels.example.repositories;

import com.hotels.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<User,Long> {

    @Override
    Optional<User> findById(Long id);

    @Override
    <S extends User> S save(S entity);


    @Override
    void deleteById(Long id);






    @Query(
            value = "select count(*) from users join \n" +
                    "\n" +
                    "user_roles  on user_roles.user_id=users.id\n" +
                    "\n" +
                    "join roles on roles.id=user_roles.roles_id where roles.type_role='EMPLOYEE'",
            nativeQuery = true)
    long  countEmployess();


}
