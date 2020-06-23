package com.cheeringlocalrestaurant.domain.model.order;

import java.math.BigDecimal;

import javax.validation.Valid;

import com.cheeringlocalrestaurant.domain.type.menu.MenuId;
import com.cheeringlocalrestaurant.domain.type.menu.MenuName;
import com.cheeringlocalrestaurant.domain.type.menu.TaxIncludedUnitPrice;
import com.cheeringlocalrestaurant.domain.type.order.AmountOfMoney;
import com.cheeringlocalrestaurant.domain.type.order.OrderDetailId;
import com.cheeringlocalrestaurant.domain.type.order.Quantity;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class OrderDetail {

    @NonNull
    @Valid
    private OrderDetailId orderDetailId;
    
    @NonNull
    @Valid
    private MenuId menuId;
    
    @NonNull
    @Valid
    private MenuName menuName;
    
    @NonNull
    @Valid
    private TaxIncludedUnitPrice taxIncludedUnitPrice;
    
    @NonNull
    @Valid
    private Quantity quantity;
    
    private AmountOfMoney detailTotalAmount;

    // @formatter:off
    public OrderDetail(Long orderDetailId, Long menuId, String menuName,
            Short taxIncludedUnitPrice, Short quantity) {
        this(new OrderDetailId(orderDetailId), 
                new MenuId(menuId), 
                new MenuName(menuName), 
                new TaxIncludedUnitPrice(taxIncludedUnitPrice), 
                new Quantity(quantity));
    }

    public OrderDetail(OrderDetailId orderDetailId, MenuId menuId, MenuName menuName,
            TaxIncludedUnitPrice taxIncludedUnitPrice, Quantity quantity) {
        this.orderDetailId = orderDetailId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.taxIncludedUnitPrice = taxIncludedUnitPrice;
        this.quantity = quantity;
        
        BigDecimal bgUnitPrice = new BigDecimal(taxIncludedUnitPrice.getValue());
        BigDecimal bgQuantity = new BigDecimal(quantity.getValue());
        BigDecimal bgTotalAmount = bgUnitPrice.multiply(bgQuantity);
        this.detailTotalAmount = new AmountOfMoney( bgTotalAmount.intValue() );
    }
    // @formatter:on
}
