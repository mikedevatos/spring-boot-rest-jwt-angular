package com.hotels.example.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class Utilities {

  static Logger log = LoggerFactory.getLogger(Utilities.class);

    Utilities() {
   }



   public static String getToken(String username, String password, MockMvc mockMvc) throws Exception {
      String content = mockMvc.perform(post("/api/login")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .content("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}"))
              .andReturn()
              .getResponse()
       .getHeader("Authorization").toString().replace("Bearer","");




         log.debug("token  is"+  content);


       return  content;
   }


}
