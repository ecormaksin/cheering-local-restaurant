package com.cheeringlocalrestaurant.infra.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoName;

public interface RestoNameRepository extends JpaRepository<RestoName, Long> {

}
