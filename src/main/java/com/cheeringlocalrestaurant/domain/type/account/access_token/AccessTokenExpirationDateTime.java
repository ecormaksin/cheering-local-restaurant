package com.cheeringlocalrestaurant.domain.type.account.access_token;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AccessTokenExpirationDateTime extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotNull
    private final LocalDateTime value;

    public AccessTokenExpirationDateTime(final LocalDateTime value) {
        this.value = value;
        this.validate(this);
    }
}
