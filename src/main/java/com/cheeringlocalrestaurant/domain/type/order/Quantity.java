package com.cheeringlocalrestaurant.domain.type.order;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Quantity extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    @Max(99)
    private final Short value;

    public Quantity(final Short value) {
        this.value = value;

        this.validate(this);
    }
}
