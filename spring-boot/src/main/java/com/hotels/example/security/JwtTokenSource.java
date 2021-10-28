package com.hotels.example.security;

import org.springframework.stereotype.Component;

@Component
public   class JwtTokenSource {
    public static final String key ="N8nodvN5iFN5HOir6xjYdqSmO0n0WhQHShXdKzYiiA1i71I2W5qlyUbxASfXGGCSi0u9WCdFbJAxoObaFajXAw";
    public static final String header = "Authorization";
    public static final String _prefix = "Bearer ";
    public static final int duration = 30000000;
}