package com.cheeringlocalrestaurant.infra.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheeringlocalrestaurant.infra.db.jpa.entity.LoginRequest;

public interface LoginRequestRepository extends JpaRepository<LoginRequest, Long> {

}
