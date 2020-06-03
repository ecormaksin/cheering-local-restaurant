package com.cheeringlocalrestaurant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLTemplates;

@Configuration
public class SQLTemplatesConfig {

    @Bean
    SQLTemplates sqlTemplates() {
        return new OracleTemplates();
    }
}
