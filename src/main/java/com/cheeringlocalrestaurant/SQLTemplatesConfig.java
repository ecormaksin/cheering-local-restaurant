package com.cheeringlocalrestaurant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.querydsl.sql.H2Templates;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLTemplates;

@Configuration
public class SQLTemplatesConfig {

    @Bean
    @Profile("!dev_db_h2")
    SQLTemplates sqlTemplatesOracle() {
        return new OracleTemplates();
    }

    @Bean
    @Profile("dev_db_h2")
    SQLTemplates sqlTemplatesH2() {
        return new H2Templates();
    }
}
