package com.cheeringlocalrestaurant.domain.type;

import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RemoteIpAddress extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotAllBlank
    private final String value;

    public RemoteIpAddress(final String value) {
        this.value = value;
        this.validate(this);
    }
}
