package com.cheeringlocalrestaurant.infra.db;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class JavaTimeDateConverter {

    public static Date to(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
