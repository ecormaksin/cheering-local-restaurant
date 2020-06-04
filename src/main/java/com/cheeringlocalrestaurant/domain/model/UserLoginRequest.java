package com.cheeringlocalrestaurant.domain.model;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.account.UserId;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessToken;
import com.cheeringlocalrestaurant.domain.type.account.login.AccessTokenExpirationDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Getter
public class UserLoginRequest {

    @NonNull
    @Valid
    private UserId userId;
    @NonNull
    @Valid
    private MailAddress mailAddress;
    @NonNull
    @Valid
    private AccessToken accessToken;
    @NonNull
    @Valid
    private AccessTokenExpirationDateTime accessTokenExpirationDateTime;
    
    public UserLoginRequest(Long userId, String mailAddress, String accessToken, LocalDateTime accessTokenExpirationDateTime) {
        this.userId = new UserId(userId);
        this.mailAddress = new MailAddress(mailAddress);
        this.accessToken = new AccessToken(accessToken);
        this.accessTokenExpirationDateTime = new AccessTokenExpirationDateTime(accessTokenExpirationDateTime);
    }
}
