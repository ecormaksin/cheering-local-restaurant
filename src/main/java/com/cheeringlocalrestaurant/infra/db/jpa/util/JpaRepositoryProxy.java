package com.cheeringlocalrestaurant.infra.db.jpa.util;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;

public final class JpaRepositoryProxy {

    public static <E,K> E save(JpaRepository<E, K> repository, E entity, RemoteIpAddress remoteIpAddress) {
        EntityUtil.setCommonColumn(entity, remoteIpAddress);
        return entity = repository.save(entity);
    }
}
