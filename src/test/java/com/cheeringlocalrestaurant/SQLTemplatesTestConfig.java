package com.cheeringlocalrestaurant;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLTemplates;

@TestConfiguration
public class SQLTemplatesTestConfig {

    @Bean
    @Primary
    SQLTemplates sqlTemplatesTest() {
        return new H2Templates();
    }
}
