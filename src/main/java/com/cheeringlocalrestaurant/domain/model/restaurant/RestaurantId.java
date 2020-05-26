package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.io.Serializable;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class RestaurantId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Long value;
}
