package com.cheeringlocalrestaurant.domain.type.time;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class StartTime extends Time {

	private static final long serialVersionUID = 1L;

	public StartTime(final String timeString) {
		super(timeString);
	}
}
