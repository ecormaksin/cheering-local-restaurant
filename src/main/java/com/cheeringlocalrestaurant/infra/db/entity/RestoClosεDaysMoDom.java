package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_CLOSΕ_DAYS_MO_DOM database table.
 * 
 */
@Entity
@Table(name="RESTO_CLOSΕ_DAYS_MO_DOM")
@NamedQuery(name="RestoClosεDaysMoDom.findAll", query="SELECT r FROM RestoClosεDaysMoDom r")
public class RestoClosεDaysMoDom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DAY_OF_MONTH")
	private BigDecimal dayOfMonth;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	public RestoClosεDaysMoDom() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDayOfMonth() {
		return this.dayOfMonth;
	}

	public void setDayOfMonth(BigDecimal dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public BigDecimal getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(BigDecimal restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}