package com.cheeringlocalrestaurant.infra.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheeringlocalrestaurant.infra.db.jpa.entity.RestoHistory;

public interface RestoHistoryRepository extends JpaRepository<RestoHistory, Long> {

}
