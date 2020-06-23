package com.cheeringlocalrestaurant.domain.type.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AmountOfMoney extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private final Integer value;

    public AmountOfMoney(final Integer value) {
        this.value = value;

        this.validate(this);
    }
}
