package com.cheeringlocalrestaurant.domain.model.restaurant.openhour;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.Getter;

public abstract class OpenHourPart implements Serializable, Comparable<OpenHourPart> {

	private static final long serialVersionUID = 1L;

	@Getter
	protected LocalTime value;
	
	public OpenHourPart(final String timeString) {
		try {
			this.value = LocalTime.parse(timeString);
		} catch(Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public String toString() {
		return this.value.toString();
	}
	
	public boolean equals(OpenHourPart other) {
		return this.toString().equals(other.toString());
	}
	
	public int hashCode() {
		return this.value.hashCode();
	}
	
	public int compareTo(OpenHourPart other) {
		return this.toString().compareTo(other.toString());
	}
}
