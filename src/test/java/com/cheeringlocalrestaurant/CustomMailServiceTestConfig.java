package com.cheeringlocalrestaurant;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.cheeringlocalrestaurant.infra.mail.CustomMailService;
import com.cheeringlocalrestaurant.infra.mail.CustomMailServiceTestImpl;

@TestConfiguration
public class CustomMailServiceTestConfig {

    @Bean
    CustomMailService customMailService() {
        return new CustomMailServiceTestImpl();
    }
}
