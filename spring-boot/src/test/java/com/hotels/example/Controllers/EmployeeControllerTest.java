package com.hotels.example.Controllers;


import com.hotels.example.TestUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private  MockMvc getMockMvc() {
        return mockMvc;
    }


    @Test
    @SneakyThrows
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void createEmployeeAndExpectOk(){
        String token = TestUtil.getToken("user1", "pass1", getMockMvc());
        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(TestUtil.bytesFromPath(new ClassPathResource("/src/test/resources/employee_files/post_employee.json").getPath()))
                )
                .andExpect(status().isCreated());
    }


    @Test
    @SneakyThrows
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void updateEmployeeAndExpectOk(){
        String token = TestUtil.getToken("user1", "pass1", getMockMvc());
        mockMvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(TestUtil.bytesFromPath(new ClassPathResource("/src/test/resources/employee_files/put_employee.json").getPath()))
                )
                .andExpect(status().isAccepted());
    }




}
