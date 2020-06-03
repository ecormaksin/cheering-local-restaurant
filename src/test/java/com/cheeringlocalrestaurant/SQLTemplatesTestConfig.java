package com.cheeringlocalrestaurant;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLTemplates;

@TestConfiguration
public class SQLTemplatesTestConfig {

    @Bean
    SQLTemplates sqlTemplates() {
        return new H2Templates();
    }
}
