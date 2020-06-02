package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cheeringlocalrestaurant.CheeringLocalRestaurantApplication;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QResto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoName;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLTemplates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestEntityManager
@Transactional
class RestaurantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
    private JPAQueryFactory queryFactory;
    
    @Autowired
    private SQLTemplates templates;
    
    @Autowired
    private RestaurantRepository restaurantRepository;
    
    @TestConfiguration
    static class TestConfig {
        @Bean
        SQLTemplates templates() {
            return new H2Templates();
        }
    }

    @BeforeEach
    void setup() {
        queryFactory = new JPAQueryFactory(entityManager.getEntityManager());
    }

    @AllArgsConstructor
    @Getter
    class RestaurantAccountDTO {

        private Long userId;
        private Long restaurantId;
        private String restaurantName;
        private String mailAddress;
        
        protected RestaurantAccountDTO() {}
    }

    @Test
    void _仮登録() {
        final String name = "いろは食堂";
        final String email = "iroha@example.com";

        RestaurantTempRegister tempRegister = new RestaurantTempRegister(name, email);
        RestaurantId idActual = restaurantRepository.save(tempRegister, "127.0.0.1");
        assertNotNull(idActual);
        assertNotNull(idActual.getValue());
        
        QUser qUser = QUser.user;
        QRestoAccount qRestoAccount = QRestoAccount.restoAccount;
        QResto qResto = QResto.resto;
        QRestoHistory qRestoHistory = QRestoHistory.restoHistory;
        QRestoName qRestoName = QRestoName.restoName;
        JPASQLQuery<?> query = new JPASQLQuery<Void>(entityManager.getEntityManager(), templates);
        RestaurantAccountDTO restaurantAccountDTO 
                = query.select(Projections.constructor(RestaurantAccountDTO.class, 
                        qUser.userId, qResto.restaurantId, qRestoName.restaurantName, qUser.mailAddress))
                        .from(qUser)
                            .innerJoin(qRestoAccount)
                        .fetchOne();
    }

}
