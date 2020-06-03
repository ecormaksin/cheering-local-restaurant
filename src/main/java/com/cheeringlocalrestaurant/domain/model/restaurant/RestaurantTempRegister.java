package com.cheeringlocalrestaurant.domain.model.restaurant;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.account.MailAddress;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class RestaurantTempRegister {

    @NonNull
    @Valid
    private RestaurantName name;
    @NonNull
    @Valid
    private MailAddress mailAddress;

    public RestaurantTempRegister(String name, String mail) {
        this.name = new RestaurantName(name);
        this.mailAddress = new MailAddress(mail);
    }
}
