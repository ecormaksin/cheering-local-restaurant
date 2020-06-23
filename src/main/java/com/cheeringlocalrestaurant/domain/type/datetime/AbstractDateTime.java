package com.cheeringlocalrestaurant.domain.type.datetime;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;

public abstract class AbstractDateTime implements Serializable, Comparable<AbstractDateTime> {

    private static final long serialVersionUID = 1L;

    @Getter
    protected LocalDateTime value;
    
    public AbstractDateTime(final String dateString) {
        try {
            this.value = LocalDateTime.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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
