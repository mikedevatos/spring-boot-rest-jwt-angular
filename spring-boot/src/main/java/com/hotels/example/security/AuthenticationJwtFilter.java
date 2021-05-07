package com.hotels.example.security;

import com.google.gson.Gson;
import com.hotels.example.dto.LoginUser;

import com.hotels.example.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AuthenticationJwtFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationJwtFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/login");
    }

    //get login   username,password   from login request
    private LoginUser getLoginRequest(HttpServletRequest request) {
        BufferedReader reader = null;
        LoginUser loginRequest = null;
        try {
            reader = request.getReader();
            Gson gson = new Gson();
            loginRequest = gson.fromJson(reader, LoginUser.class);
        } catch (IOException ex) {
            Logger.getLogger(AuthenticationJwtFilter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(AuthenticationJwtFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (loginRequest == null) {
            loginRequest = new LoginUser();
        }

        return loginRequest;
    }


   // authenticate login request
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginUser login = null;
        try {
          login = getLoginRequest(request);

         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(),
                                                                                                           login.getPassword(),
                                                                                                           new ArrayList<>()  );

            Authentication auth = authenticationManager.authenticate(authenticationToken);
            return auth;
        }
        catch(AuthenticationException e){
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

  // make and send bearer_token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User principal = (User) authResult.getPrincipal();


         //make  bearer_token
        String token=Jwts.builder()
                    .setExpiration(new Date(System.currentTimeMillis() + JwtTokenSource.duration))
                    .setSubject(principal.getUsername())
                    .signWith(SignatureAlgorithm.HS256, JwtTokenSource.key.getBytes())
                    .compact();

        //send bearer_token
        response.addHeader(JwtTokenSource.header, JwtTokenSource._prefix +token);
    }
}
