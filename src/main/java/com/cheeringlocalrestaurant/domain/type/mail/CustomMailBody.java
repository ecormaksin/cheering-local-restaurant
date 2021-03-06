package com.cheeringlocalrestaurant.domain.type.mail;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CustomMailBody extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotAllBlank
    private final String value;

    public CustomMailBody(final String value) {
        this.value = value;
        this.validate(this);
    }
}
