package com.cheeringlocalrestaurant.infra.db.jpa.util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.cheeringlocalrestaurant.domain.type.RemoteIpAddress;
import com.cheeringlocalrestaurant.infra.db.JavaTimeDateConverter;

final class EntityUtil {

    public static <E> void setCommonColumn(E entity, RemoteIpAddress remoteIpAddress) {
        setStartDate(entity);
        setEndDate(entity);
        setRegisteredTimestamp(entity);
        setRemoteIpAddress(entity, remoteIpAddress);
    }

    private static <E> void setStartDate(E entity) {
        setDate(entity, "startDate", LocalDate.of(1900, 1, 1));
    }

    private static <E> void setEndDate(E entity) {
        setDate(entity, "endDate", LocalDate.of(9999, 12, 31));
    }

    private static <E> void setDate(E entity, String fieldName, LocalDate defaultLocalDate) {
        try {
            Field field = getField(entity, fieldName);
            Date date = (Date) field.get(entity);
            if (null != date) return;
            date = JavaTimeDateConverter.toDateFromLocalDate(defaultLocalDate);
            field.set(entity, date);
        } catch (Exception e) {

        }
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