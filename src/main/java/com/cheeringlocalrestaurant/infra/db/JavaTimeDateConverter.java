package com.cheeringlocalrestaurant.infra.db;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class JavaTimeDateConverter {

    public static Date toDateFromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static Timestamp toTimestampFromLocalDateTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
    
    public static LocalDateTime toLocalDateTimeFromTimestamp(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
