package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.cheeringlocalrestaurant.presentation.controller.restaurant.RestaurantTempRegisterForm;
import com.cheeringlocalrestaurant.validation.notallblank.NotAllBlank;

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
    
	@NotAllBlank(max = MAX_SIZE)
    private final String value;
    
    public RestaurantName(final String value) {
    	this.value = value;

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<RestaurantName>> violations = validator.validate(this);
		if (violations.size() != 0 ) throw new IllegalArgumentException();
    }
}
