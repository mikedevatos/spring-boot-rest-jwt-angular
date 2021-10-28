package com.hotels.example.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCacheConfig {


    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("customers",
                                                                                 "roomBookings",
                                                                                 "employees"
        );

        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }


    Caffeine<Object, Object > caffeineCacheBuilder()
    {
        return Caffeine.newBuilder()
                       .maximumSize(3000)
                       .expireAfterAccess(10, TimeUnit.MINUTES)
                       .weakKeys()
                       .recordStats();
    }



}
