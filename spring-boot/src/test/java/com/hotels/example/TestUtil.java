package com.hotels.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
public class TestUtil {

    public static String getToken(String username, String password, MockMvc mockMvc) throws Exception {
        String content = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}"))
                .andReturn()
                .getResponse()
                .getHeader("Authorization").toString().replace("Bearer ", "");

        log.debug("token  is "+  content);
        return  content;
    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @SneakyThrows
    public static byte[] bytesFromPath(final String path) {
        return Files.readAllBytes(Paths.get(path));
    }


}
