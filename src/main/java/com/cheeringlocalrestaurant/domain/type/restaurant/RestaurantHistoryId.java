package com.cheeringlocalrestaurant.domain.type.restaurant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RestaurantHistoryId extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private final Long value;

    public RestaurantHistoryId(final Long value) {
        this.value = value;

        this.validate(this);
    }
}
