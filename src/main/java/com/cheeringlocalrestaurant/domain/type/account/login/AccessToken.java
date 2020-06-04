package com.cheeringlocalrestaurant.domain.type.account.login;

import java.util.UUID;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AccessToken extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotAllBlank
    private final String value;

    public AccessToken() {
        this.value = UUID.randomUUID().toString();
    }
    
    public AccessToken(final String value) {
        this.value = value;
        this.validate(this);
    }
}
