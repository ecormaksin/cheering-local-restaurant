package com.cheeringlocalrestaurant.domain.type.datetime;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.cheeringlocalrestaurant.domain.type.date.AbstractDate;
import com.cheeringlocalrestaurant.domain.type.time.AbstractTime;

import lombok.Getter;

public abstract class AbstractDateTime implements Serializable, Comparable<AbstractDateTime> {

    private static final long serialVersionUID = 1L;

    @Getter
    protected LocalDateTime value;
    
    public AbstractDateTime(final String dateTimeString) {
        Throwable cause = null;
        for (String format : AbstractDate.formats()) {
            try {
                this.value = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(format + " " + AbstractTime.FORMAT_STRING));
                return;
            } catch (Exception e) {
                cause = e;
            }
        }
        throw new IllegalArgumentException(cause);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public boolean equals(AbstractDateTime other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int compareTo(AbstractDateTime other) {
        return this.toString().compareTo(other.toString());
    }
}
