package com.cheeringlocalrestaurant.domain.model.restaurant.openhour;

import com.cheeringlocalrestaurant.domain.type.time.EndTime;
import com.cheeringlocalrestaurant.domain.type.time.StartTime;

import lombok.Getter;

@Getter
public class OpenHour {

    private StartTime startTime;
    private EndTime endTime;

    public OpenHour(final StartTime startTime, final EndTime endTime) {
        if (startTime.equals(endTime))
            throw new IllegalArgumentException("StarTime is the same as EndTime.");

        if (startTime.toString().compareTo(endTime.toString()) > 0) {
            this.startTime = new StartTime(endTime.toString());
            this.endTime = new EndTime(startTime.toString());
            return;
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OpenHour(final String startTimeString, final String endTimeString) {
        this(new StartTime(startTimeString), new EndTime(endTimeString));
    }

    public String toString() {
        return String.format("%s〜%s", this.startTime.toString(), this.endTime.toString());
    }

    public boolean equals(OpenHour other) {
        return this.toString().equals(other.toString());
    }

    public int hashCode() {
        return this.startTime.hashCode() + this.endTime.hashCode();
    }

    public boolean isOverlapped(OpenHour other) {
        return this.startTime.compareTo(other.endTime) < 0 && other.startTime.compareTo(this.endTime) < 0;
    }

}
