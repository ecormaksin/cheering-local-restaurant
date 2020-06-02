package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantAccount;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantRepository;
import com.cheeringlocalrestaurant.domain.model.restaurant.RestaurantTempRegister;
import com.cheeringlocalrestaurant.domain.type.UserRole;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QResto;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoAccount;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoHistory;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QRestoName;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLTemplates;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestEntityManager
@Transactional
@Slf4j
class RestaurantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    
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
        Date systemDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        // @formatter:off
        RestaurantAccount restaurantAccount 
                = query.select(Projections.constructor(RestaurantAccount.class, 
                        qUser.userId, qResto.restaurantId, qRestoName.restaurantName, qUser.mailAddress))
                        .from(qUser)
                            .innerJoin(qRestoAccount)
                                .on(qUser.userId.eq(qRestoAccount.userId))
                            .innerJoin(qResto)
                                .on(qRestoAccount.restaurantId.eq(qResto.restaurantId))
                            .innerJoin(qRestoHistory)
                                .on(qResto.restaurantId.eq(qRestoHistory.restaurantId))
                            .innerJoin(qRestoName)
                                .on(qRestoHistory.restaurantHistoryId.eq(qRestoName.restaurantHistoryId))
                        .where(qUser.mailAddress.eq(email)
                                .and(qUser.userRole.eq(UserRole.RESTAURANT_ADMIN.getValue()))
                                .and(qRestoHistory.startDate.loe(systemDate))
                                .and(qRestoHistory.endDate.goe(systemDate))
                        ).fetchOne();
        // @formatter:on
        assertNotNull(restaurantAccount);
        assertNotNull(restaurantAccount.getUserId());
        assertNotNull(restaurantAccount.getRestaurantId());
        assertEquals(name, restaurantAccount.getRestaurantName().getValue());
        assertEquals(email, restaurantAccount.getMailAddress().getValue());
        log.info(restaurantAccount.toString());
    }

}
