package com.cheeringlocalrestaurant.domain.model.openhour;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public class StartTime extends OpenHourPart {

	private static final long serialVersionUID = 1L;

	public StartTime(final String timeString) {
		super(timeString);
	}
}
