package com.cheeringlocalrestaurant.domain.type.time;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

public abstract class AbstractTime implements Serializable, Comparable<AbstractTime> {

    public static final int BASE_HOURS_OVER_A_DAY = 24;
    public static final String FORMAT_STRING = "HH:mm";


    private static final long serialVersionUID = 1L;
    
    private static String localTimeMin;
    private static String localTimeMax;
    
    static {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT_STRING);
        localTimeMin = LocalTime.MIN.format(dtf);
        localTimeMax = LocalTime.MAX.format(dtf);
    }
    
    public static String min() {
        return localTimeMin;
    }
    
    public static String max() {
        return localTimeMax;
    }
    
    @Getter
    protected LocalTime value;

    public AbstractTime(final String timeString) {
        try {
            this.value = LocalTime.parse(timeString);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public boolean equals(AbstractTime other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int compareTo(AbstractTime other) {
        return this.toString().compareTo(other.toString());
    }
}
