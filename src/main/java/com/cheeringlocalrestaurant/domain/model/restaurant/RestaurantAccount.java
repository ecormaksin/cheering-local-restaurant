package com.cheeringlocalrestaurant.domain.model.restaurant;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.MailAddress;
import com.cheeringlocalrestaurant.domain.type.UserId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Getter
public class RestaurantAccount {

    @NonNull
    @Valid
    private UserId userId;
    @NonNull
    @Valid
    private RestaurantId restaurantId;
    @NonNull
    @Valid
    private RestaurantName restaurantName;
    @NonNull
    @Valid
    private MailAddress mailAddress;
    
    public RestaurantAccount(Long userId, Long restaurantId, String restaurantName, String mailAddress) {
        this.userId = new UserId(userId);
        this.restaurantId = new RestaurantId(restaurantId);
        this.restaurantName = new RestaurantName(restaurantName);
        this.mailAddress = new MailAddress(mailAddress);
    }
    
    public String toString() {
        // @formatter:off
        return String.format("userId: %s, restaurantId: %s, restaurantName: %s, mailAddress: %s", 
                userId.getValue().toString(),
                restaurantId.getValue().toString(),
                restaurantName.getValue()
                ,mailAddress.getValue());
        // @formatter:on
    }
}
