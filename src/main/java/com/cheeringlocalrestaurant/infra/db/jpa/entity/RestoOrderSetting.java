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
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Column(name="EARLIEST_READY_MINUTES")
	private Integer earliestReadyMinutes;

	@Column(name="ORDER_AVAILABLE_FUTURE_DAYS")
	private Integer orderAvailableFutureDays;

	public RestoOrderSetting() {
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Integer getEarliestReadyMinutes() {
		return this.earliestReadyMinutes;
	}

	public void setEarliestReadyMinutes(Integer earliestReadyMinutes) {
		this.earliestReadyMinutes = earliestReadyMinutes;
	}

	public Integer getOrderAvailableFutureDays() {
		return this.orderAvailableFutureDays;
	}

	public void setOrderAvailableFutureDays(Integer orderAvailableFutureDays) {
		this.orderAvailableFutureDays = orderAvailableFutureDays;
	}

}