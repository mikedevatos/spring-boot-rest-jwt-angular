package com.hotels.example.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotels.example.TestUtil;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void before(){
    }

    @After
    public void after()  {
    }

    private  MockMvc getMockMvc() {
        return mockMvc;
    }


   @Test
   @SneakyThrows
  public void getPageCustomers_AndExpectOk() {
       String token = TestUtil.getToken("user1", "pass1", getMockMvc());
       mockMvc.perform(get("/api/customer/{page}/{size}", 0,5)
                       .contentType(MediaType.APPLICATION_JSON)
                       .header(HttpHeaders.AUTHORIZATION,"Bearer "+token))
                       .andExpect(status().isOk());

   }

    @Test
    @SneakyThrows
    public void getPageCustomers_WithOutAuthAndExpectForbidden(){
        mockMvc.perform(get("/api/customer/{page}/{size}", 0,5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    public void getPageCustomers_WithMalformedAuthAndExpectBadRequest(){
        String token = TestUtil.getToken("user1", "pass1", getMockMvc());

        mockMvc.perform(get("/api/customer/{page}/{size}", 0,5)
                        .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token+"sometext"))
                .andExpect( status().isBadRequest() );
    }


   @Test
   @SneakyThrows
   @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
 public void createCustomerAndExpectOk(){
       String token = TestUtil.getToken("user1", "pass1", getMockMvc());
     mockMvc.perform(post("/api/customer")
             .contentType(MediaType.APPLICATION_JSON)
             .header("Authorization", "Bearer " + token)
             .content(TestUtil.bytesFromPath(new ClassPathResource("/src/test/resources/customers_files/post_customer.json").getPath()))
            )
             .andExpect(status().isCreated());
   }

    @Test
    @SneakyThrows
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void updateCustomerAndExpectOk(){
        String token = TestUtil.getToken("user1", "pass1", getMockMvc());

        final ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(TestUtil.bytesFromPath(new ClassPathResource("/src/test/resources/customers_files/put_customer_replacing_room.json").getPath()))
                )
                .andExpect( status().isAccepted() );
    }



}
