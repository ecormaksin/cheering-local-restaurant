package com.cheeringlocalrestaurant.domain.type.time;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.Getter;

public class Time implements Serializable, Comparable<Time> {

	private static final long serialVersionUID = 1L;

	@Getter
	protected LocalTime value;
	
	public Time(final String timeString) {
		try {
			this.value = LocalTime.parse(timeString);
		} catch(Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public String toString() {
		return this.value.toString();
	}
	
	public boolean equals(Time other) {
		return this.toString().equals(other.toString());
	}
	
	public int hashCode() {
		return this.value.hashCode();
	}
	
	public int compareTo(Time other) {
		return this.toString().compareTo(other.toString());
	}
}
