package com.hotels.example.test;


import com.hotels.example.service.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControlleRespones {
    private static final int pageSize = 5;


    @Autowired
    private EmployeeServiceImpl employeeService;


    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }


    @Test
    public void getEmployeesWithTokenOk() throws Exception {


        String token = Utilities.getToken("user1", "pass1", getMockMvc());



        long totalEmployees = employeeService.count();
        int totalPages = (int) (totalEmployees/pageSize) + 1;


        for (int i = 0; i < totalPages; i++)
        {
            getMockMvc().perform(get("/api/employee/"+i+"/"+pageSize)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + token))
                    .andExpect(status().isOk());
        }
              }


    @Test
    public void getEmployeesWithoutToken() throws Exception {


        String token = Utilities.getToken("user1", "pass1", getMockMvc());

        getMockMvc().perform(get("/api/employee/1/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }




}
