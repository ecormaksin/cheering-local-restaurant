package com.cheeringlocalrestaurant;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.cheeringlocalrestaurant.infra.mail.CustomMailService;
import com.cheeringlocalrestaurant.infra.mail.CustomMailServiceTestImpl;

@TestConfiguration
public class CustomMailServiceTestConfig {

    @Bean
    @Primary
    CustomMailService customMailServiceTest() {
        return new CustomMailServiceTestImpl();
    }
}
