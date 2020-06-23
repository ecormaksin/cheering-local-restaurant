package com.cheeringlocalrestaurant.domain.type.date;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;

public abstract class AbstractDate implements Serializable, Comparable<AbstractDate> {

    private static final long serialVersionUID = 1L;

    @Getter
    protected LocalDate value;
    
    public AbstractDate(final String dateString) {
        try {
            this.value = LocalDate.parse(dateString);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public boolean equals(AbstractDate other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int compareTo(AbstractDate other) {
        return this.toString().compareTo(other.toString());
    }
}
