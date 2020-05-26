package com.cheeringlocalrestaurant.infra.db.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheeringlocalrestaurant.infra.db.jpa.entity.Resto;

public interface RestroRepository extends JpaRepository<Resto, Long> {

}
