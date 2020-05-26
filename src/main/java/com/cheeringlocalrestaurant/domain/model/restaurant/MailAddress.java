package com.cheeringlocalrestaurant.domain.model.restaurant;

import java.io.Serializable;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class MailAddress implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String value;
}
