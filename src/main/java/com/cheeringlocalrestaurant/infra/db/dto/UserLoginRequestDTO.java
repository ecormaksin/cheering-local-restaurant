package com.cheeringlocalrestaurant.infra.db.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private String mailAddress;
    private String accessToken;
    private Timestamp accessTokenExpirationDateTime;
}
