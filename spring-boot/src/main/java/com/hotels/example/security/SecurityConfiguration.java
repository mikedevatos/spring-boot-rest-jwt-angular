package com.hotels.example.security;


import com.hotels.example.repositories.UserRepo;
import com.hotels.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {





    private UserDetailsServiceImpl userDetailsServiceImpl;
    private UserRepo userRepository;
    private AuthorizationJwtFilter authorizationJwtFilter;



    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsServiceImpl,
                                 UserRepo userRepository,
                                 AuthorizationJwtFilter authorizationJwtFilter) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.userRepository = userRepository;
        this.authorizationJwtFilter = authorizationJwtFilter;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http

              .csrf().disable()


                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilter(new AuthenticationJwtFilter(authenticationManager()))
                .addFilterBefore(authorizationJwtFilter, AuthenticationJwtFilter.class)



                .authorizeRequests()

                .antMatchers( "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/*.js").permitAll()

                .antMatchers( "/h2-console/**").permitAll()

                .antMatchers(HttpMethod.GET,"/api/userinfo").permitAll()

                .antMatchers(HttpMethod.POST, "/login").permitAll()

                .antMatchers(HttpMethod.GET,"/api/room").hasAnyRole("MANAGER","EMPLOYEE")

                .antMatchers("/api/customer/*/*").hasAnyRole("EMPLOYEE","MANAGER")


                .antMatchers("/api/employee/**/*").hasRole("MANAGER")

                .antMatchers(HttpMethod.GET, "/v2/**").access("hasIpAddress('127.0.0.0/8') or hasIpAddress('::1')")
                .antMatchers( "/swagger-ui.html/**").access("hasIpAddress('127.0.0.0/8') or hasIpAddress('::1')")
                .antMatchers( "/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**").access("hasIpAddress('127.0.0.0/8') or hasIpAddress('::1')")


                .antMatchers(String.valueOf(EndpointRequest.toAnyEndpoint())).authenticated().anyRequest()
                .access("hasIpAddress('127.0.0.0/8') or hasIpAddress('::1')")

                .and()

             .httpBasic().disable()
                .headers().frameOptions().disable()
                .and()
                .cors();

    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsServiceImpl);

        return daoAuthenticationProvider;
    }
//       also change data.sql
//            @Bean
//            public PasswordEncoder getPasswordEncoder(){
//                  //Not encoding password only for developent!!!
//                return NoOpPasswordEncoder.getInstance();
//        }


         //  encoding password   also change data.sql
              @Bean
              PasswordEncoder getPasswordEncoder() {
               return new BCryptPasswordEncoder();
             }




}





