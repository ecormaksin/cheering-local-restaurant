package com.cheeringlocalrestaurant.infra.db.jpa;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class EntityUtil {

    public static <E> void setCommonColumn(E entity, String remoteIpAddress) {
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
    
    private static <E> void setRemoteIpAddress(E entity, String remoteIpAddress) {
        try {
            Field field = getField(entity, "remoteIpAddress");
            field.set(entity, remoteIpAddress);
        } catch (Exception e) {

        }
    }

    private static <E> Field getField(E entity, String fieldName) throws Exception {
        Field field = entity.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
}