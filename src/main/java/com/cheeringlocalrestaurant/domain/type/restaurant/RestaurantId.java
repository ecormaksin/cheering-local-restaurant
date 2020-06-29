package com.cheeringlocalrestaurant.domain.type.restaurant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;

import lombok.Getter;

@Getter
public class RestaurantId extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private final Long value;

    public RestaurantId(final Long value) {
        this.value = value;
        this.validate(this);
    }
    
    public boolean equals(RestaurantId other) {
        return this.value == other.value;
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
