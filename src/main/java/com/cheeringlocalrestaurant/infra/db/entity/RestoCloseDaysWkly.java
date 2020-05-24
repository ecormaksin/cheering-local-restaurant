package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_CLOSE_DAYS_WKLY database table.
 * 
 */
@Entity
@Table(name="RESTO_CLOSE_DAYS_WKLY")
@NamedQuery(name="RestoCloseDaysWkly.findAll", query="SELECT r FROM RestoCloseDaysWkly r")
public class RestoCloseDaysWkly implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DAY_OF_WEEK")
	private BigDecimal dayOfWeek;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	public RestoCloseDaysWkly() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDayOfWeek() {
		return this.dayOfWeek;
	}

	public void setDayOfWeek(BigDecimal dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public BigDecimal getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(BigDecimal restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}