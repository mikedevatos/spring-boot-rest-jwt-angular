package com.hotels.example.test;

import com.hotels.example.service.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerRespones {
    private static final int pageSize = 5;

    Logger log = LoggerFactory.getLogger(CustomerControllerRespones.class);



    @Autowired
    private MockMvc mockMvc;


      @Autowired
    private CustomerServiceImpl customerService;





    @Before
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }


   @Test
  public void getCustomersPageWithToken() throws Exception {
       String token = Utilities.getToken("user1", "pass1", getMockMvc());
       long totalCustomers = customerService.count();
       int totalPages = (int) (totalCustomers/pageSize) + 1;


       for (int i = 0; i < totalPages; i++)
       {
           getMockMvc().perform(get("/api/customer/"+i+"/"+pageSize)
                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                   .header("Authorization", "Bearer " + token))
                   .andExpect(status().isOk());
       }
   }


   @Test
   public void  CreateCustomerWithToken_AndExpectOk() throws Exception{
       String token = Utilities.getToken("user1", "pass1", getMockMvc());

       getMockMvc().perform(post("/api/customer")
         .contentType(MediaType.APPLICATION_JSON)
               .header("Authorization", "Bearer " + token)
               .content("{\"firstName\": \"firstNameTest\",  \"lastName\": \"lastNameTest\" ,\"email\": \"emailTest\" ,\"bill\": \"4444\" }"))
            .andExpect(status().isCreated());

   }

    }






