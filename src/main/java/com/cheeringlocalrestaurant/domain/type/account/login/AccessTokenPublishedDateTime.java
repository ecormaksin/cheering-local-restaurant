package com.cheeringlocalrestaurant.domain.type.account.login;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.cheeringlocalrestaurant.domain.type.ValidationConcern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AccessTokenPublishedDateTime extends ValidationConcern {

    private static final long serialVersionUID = 1L;

    @NotNull
    private final LocalDateTime value;
    
    public AccessTokenPublishedDateTime() {
        this.value = LocalDateTime.now();
    }

    public AccessTokenPublishedDateTime(final LocalDateTime value) {
        this.value = value;
        this.validate(this);
    }
    
    public AccessTokenExpirationDateTime accessTokenExpirationDateTime(Integer expirationHours) {
        return accessTokenExpirationDateTime(new AccessTokenExpirationHours(expirationHours));
    }

    public AccessTokenExpirationDateTime accessTokenExpirationDateTime(AccessTokenExpirationHours expirationHours) {
        final LocalDateTime localDateTime = this.value.plusHours(expirationHours.getValue());
        return new AccessTokenExpirationDateTime(localDateTime);
    }
}
