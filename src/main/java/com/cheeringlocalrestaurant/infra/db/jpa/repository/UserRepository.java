package com.cheeringlocalrestaurant.infra.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<com.cheeringlocalrestaurant.infra.db.jpa.entity.User, Long> {

}
