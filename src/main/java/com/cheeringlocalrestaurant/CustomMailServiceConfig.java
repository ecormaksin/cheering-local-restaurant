package com.cheeringlocalrestaurant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheeringlocalrestaurant.infra.mail.CustomMailService;
import com.cheeringlocalrestaurant.infra.mail.CustomMailServiceImpl;

@Configuration
public class CustomMailServiceConfig {

    @Bean
    CustomMailService customMailService() {
        return new CustomMailServiceImpl();
    }
}
