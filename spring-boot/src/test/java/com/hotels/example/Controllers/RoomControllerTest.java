package com.hotels.example.Controllers;

import com.hotels.example.TestUtil;
import com.hotels.example.errors.RoomIsBookedException;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Autowired
    private MockMvc mockMvc;

    private MockMvc getMockMvc() {
        return mockMvc;
    }


    @Test
    @SneakyThrows
    public void deleteNotEmptyRoom_andExpectBadRequest(){
        String token = TestUtil.getToken("user1", "pass1", getMockMvc());

        String roomId = "1";
        mockMvc.perform( delete("/api/room"+"/"+roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest() )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RoomIsBookedException));;

    }


}
