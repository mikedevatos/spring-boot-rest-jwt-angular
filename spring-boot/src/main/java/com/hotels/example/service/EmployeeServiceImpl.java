package com.hotels.example.service;

import com.hotels.example.model.User;
import com.hotels.example.repositories.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

  @Service
  @Transactional
  @Slf4j
public class EmployeeServiceImpl  {

     private  final String employeeRole="EMPLOYEE";
     private EmployeeRepo employeeRepo;

     @Autowired
     public EmployeeServiceImpl(EmployeeRepo employeeRepo){
         this.employeeRepo=employeeRepo;
     }

   @Transactional
   @Caching( evict = {
           @CacheEvict(value = "employees",allEntries = true,
                       cacheManager = "caffeineCacheManager",condition = "#result != null")
   })
  public void deleteEmployee(Integer id){
         employeeRepo.deleteById(id);
    }


    @Transactional
    @Caching( evict = {
            @CacheEvict(value = "employees",allEntries = true,
                        cacheManager = "caffeineCacheManager",
                        condition = "#result != null")
    })
   public User saveEmployee(User user){
     return    employeeRepo.save(user);
 }

 
    public User findBy_Id( Integer id){
        User employee = employeeRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        return employee;
    }

      @Cacheable(cacheNames="employees",
                 key="#page",
                 cacheManager = "caffeineCacheManager",
                 condition = "#result != null" )
      public List<User> findPagedEmployees(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<User> pagedEmpl = employeeRepo.findByRole_Type_Like(employeeRole,paging);
        List<User> emp = pagedEmpl.getContent();
        return  emp;
    }

}


