package com.cheeringlocalrestaurant.infra.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.cheeringlocalrestaurant.infra.db.jpa.EntityUtil;
import com.cheeringlocalrestaurant.infra.db.jpa.entity.Resto;
import com.cheeringlocalrestaurant.infra.db.jpa.repository.RestroRepository;

@DataJpaTest
class QueryDslTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RestroRepository restroRepository;

    @Test
    void test() {
        Resto restoExp = new Resto();
        restoExp.setRestaurantId(1L);
        EntityUtil.setCommonColumn(restoExp, "127.0.0.1");
        restroRepository.save(restoExp);
//	entityManager.persist(restoExp);

        Resto restoAct = restroRepository.getOne(1L);
        assertNotNull(restoAct);

//	entityManager.flush();
    }

}
