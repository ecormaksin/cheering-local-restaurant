package com.cheeringlocalrestaurant.domain.model.restaurant;

import lombok.Getter;
import lombok.Value;

/**
 * 飲食店名
 */
@Getter
@Value
public class RestaurantName {

    /** 最小文字数 */
    public static final int MIN_SIZE = 1;
    /** 最大文字数 */
    public static final int MAX_SIZE = 50;
    
    private final String value;
}
