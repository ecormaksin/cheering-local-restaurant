package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserRole;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.QUser;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.User;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.UserRepository;
import com.cheeringlocalrestaurant.infra.db.jpa.util.JpaRepositoryProxy;
import com.querydsl.jpa.impl.JPAQueryFactory;

@DataJpaTest
class QueryDslTest {

    @Autowired
    private TestEntityManager entityManager;
    
    private JPAQueryFactory queryFactory;
    
    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setup() {
        queryFactory = new JPAQueryFactory(entityManager.getEntityManager());
    }

    @Test
    void _1つのテーブルでの簡単なテスト() {
        
    	final String mailAddress = "iroha@example.com";

        QUser qUser = QUser.user;
        
        User user = new User();
        user.setMailAddress(mailAddress);
        user.setUserRole(UserRole.RESTAURANT_ADMIN.getValue());
        user = JpaRepositoryProxy.save(userRepository, user, new RemoteIpAddress("127.0.0.1"));

        assertNotNull(user.getUserId());
        
        // @formatter:off
        User searched = queryFactory.selectFrom(qUser)
                .where(qUser.mailAddress.eq(mailAddress)
                        .and(qUser.userRole.eq(UserRole.RESTAURANT_ADMIN.getValue())))
                .fetchOne();
        // @formatter:on
        
        assertNotNull(searched);
        assertEquals(user.getUserId(), searched.getUserId());
        assertEquals(mailAddress, searched.getMailAddress());
    }
    
    @Test
    void _存在しない場合のテスト() {
        
        QUser qUser = QUser.user;
        // @formatter:off
        User user = queryFactory.selectFrom(qUser)
                .where(qUser.mailAddress.eq("dummy")
                        .and(qUser.userRole.eq(UserRole.RESTAURANT_ADMIN.getValue())))
                .fetchOne();
        // @formatter:on

        assertNull(user);
    }

}
