package com.cheeringlocalrestaurant.infra.db.jpa.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;

final class EntityUtil {

    public static <E> void setCommonColumn(E entity, RemoteIpAddress remoteIpAddress) {
        setRegisteredTimestamp(entity);
        setRemoteIpAddress(entity, remoteIpAddress);
    }

    private static <E> void setRegisteredTimestamp(E entity) {
        try {
            Field field = getField(entity, "registeredTimestamp");
            final LocalDateTime localDateTime = LocalDateTime.now();
            field.set(entity, Timestamp.valueOf(localDateTime));
        } catch (Exception e) {

        }
    }

    private static <E> void setRemoteIpAddress(E entity, RemoteIpAddress remoteIpAddress) {
        try {
            Field field = getField(entity, "remoteIpAddress");
            field.set(entity, remoteIpAddress.getValue());
        } catch (Exception e) {

        }
    }

    private static <E> Field getField(E entity, String fieldName) throws Exception {
        Field field = entity.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
}