package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESTAURANT_HISTORY_ID")
	private long restaurantHistoryId;

	@Column(name="EARLIEST_READY_MINUTES")
	private BigDecimal earliestReadyMinutes;

	@Column(name="ORDER_AVAILABLE_FUTURE_DAYS")
	private BigDecimal orderAvailableFutureDays;

	public RestoOrderSetting() {
	}

	public long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public BigDecimal getEarliestReadyMinutes() {
		return this.earliestReadyMinutes;
	}

	public void setEarliestReadyMinutes(BigDecimal earliestReadyMinutes) {
		this.earliestReadyMinutes = earliestReadyMinutes;
	}

	public BigDecimal getOrderAvailableFutureDays() {
		return this.orderAvailableFutureDays;
	}

	public void setOrderAvailableFutureDays(BigDecimal orderAvailableFutureDays) {
		this.orderAvailableFutureDays = orderAvailableFutureDays;
	}

}