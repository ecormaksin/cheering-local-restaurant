package com.cheeringlocalrestaurant.infra.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoAccount;

public interface RestoAccountRepository extends JpaRepository<RestoAccount, Long> {

}
