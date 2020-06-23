package com.cheeringlocalrestaurant.domain.model.order;

import java.math.BigDecimal;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.model.Customer;
import com.cheeringlocalrestaurant.domain.type.CustomGenericCollection;
import com.cheeringlocalrestaurant.domain.type.datetime.DesiredReceiptDateTime;
import com.cheeringlocalrestaurant.domain.type.order.AmountOfMoney;
import com.cheeringlocalrestaurant.domain.type.order.OrderId;
import com.cheeringlocalrestaurant.domain.type.order.OrderStatus;
import com.cheeringlocalrestaurant.domain.type.restaurant.RestaurantId;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Order {

    @NonNull
    @Valid
    private RestaurantId restaurantId;
    
    @NonNull
    @Valid
    private OrderId orderId;
    
    @NonNull
    @Valid
    private OrderStatus orderStatus;
    
    @NonNull
    @Valid
    private Customer customer;
    
    @NonNull
    @Valid
    private DesiredReceiptDateTime desiredReceiptDateTime;
    
    @NonNull
    @Valid
    private CustomGenericCollection<OrderDetail> orderDetails;
    
    private AmountOfMoney totalAmount;

    // @formatter:off
    public Order(@NonNull @Valid RestaurantId restaurantId, @NonNull @Valid OrderId orderId, @NonNull @Valid OrderStatus orderStatus,
            @NonNull @Valid Customer customer, @NonNull @Valid DesiredReceiptDateTime desiredReceiptDateTime,
            @NonNull @Valid CustomGenericCollection<OrderDetail> orderDetails) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.desiredReceiptDateTime = desiredReceiptDateTime;
        this.orderDetails = orderDetails;
        
        BigDecimal bgTotalAmmount = orderDetails.asList()
                .stream()
                .map(e -> new BigDecimal(e.getDetailTotalAmount().getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalAmount = new AmountOfMoney(bgTotalAmmount.intValue());
    }
    // @formatter:on
}
