package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RESTO_ORDER_SETTINGS database table.
 * 
 */
@Entity
@Table(name="RESTO_ORDER_SETTINGS")
@NamedQuery(name="RestoOrderSetting.findAll", query="SELECT r FROM RestoOrderSetting r")
public class RestoOrderSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Column(name="EARLIEST_READY_MINUTES")
	private Short earliestReadyMinutes;

	@Column(name="ORDER_AVAILABLE_FUTURE_DAYS")
	private Short orderAvailableFutureDays;

	public RestoOrderSetting() {
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Short getEarliestReadyMinutes() {
		return this.earliestReadyMinutes;
	}

	public void setEarliestReadyMinutes(Short earliestReadyMinutes) {
		this.earliestReadyMinutes = earliestReadyMinutes;
	}

	public Short getOrderAvailableFutureDays() {
		return this.orderAvailableFutureDays;
	}

	public void setOrderAvailableFutureDays(Short orderAvailableFutureDays) {
		this.orderAvailableFutureDays = orderAvailableFutureDays;
	}

}