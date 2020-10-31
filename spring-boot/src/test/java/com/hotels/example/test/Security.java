package com.hotels.example.test;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Security {
    Logger log = LoggerFactory.getLogger(Security.class);




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
    public void getCurrentUsername() {
        String username=null;

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user1", "pass1"));
        SecurityContextHolder.setContext(securityContext);
        Authentication authe =  SecurityContextHolder.getContext().getAuthentication();
        if(!(authe  instanceof AnonymousAuthenticationToken  || authe==null)){
            username = authe.getPrincipal().toString();

        }

        assertThat(username).contains("user1");
    }







    @Test
    public void getUserWithRoleManagerWithToken() throws Exception {
         String token = Utilities.getToken("user1", "pass1", getMockMvc());
        log.debug("token is" +token);

        getMockMvc().perform(get("/api/userinfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\n" +
                                "  \"username\" : \"user1\",\n" +
                                "  \"role\" : \"MANAGER\"}"
                ));
    }





    @Test
    public void successfulAuthenticationWithUserManager() throws Exception {
        getMockMvc().perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"pass1\", \"username\": \"user1\"}"))
               .andExpect(status().isOk());
    }



    @Test
    public void successfulAuthenticationWithUserEmployee() throws Exception {
        getMockMvc().perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"pass3\", \"username\": \"user3\"}"))
                .andExpect(status().isOk());
    }




    @Test
    public void  tryAccessCustomersWithoutToken() throws Exception {
        getMockMvc().perform(get("/api/customer/1/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }



    @Test
    public void  getUserRoleWithoutToken() throws Exception {
        getMockMvc().perform(get("/api/userinfo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }











}
