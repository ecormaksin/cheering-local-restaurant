package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_CLOSE_DAYS_MO_DOW database table.
 * 
 */
@Entity
@Table(name="RESTO_CLOSE_DAYS_MO_DOW")
@NamedQuery(name="RestoCloseDaysMoDow.findAll", query="SELECT r FROM RestoCloseDaysMoDow r")
public class RestoCloseDaysMoDow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DAY_OF_WEEK")
	private BigDecimal dayOfWeek;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	@Column(name="WEEK_OF_MONTH")
	private BigDecimal weekOfMonth;

	public RestoCloseDaysMoDow() {
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

	public BigDecimal getWeekOfMonth() {
		return this.weekOfMonth;
	}

	public void setWeekOfMonth(BigDecimal weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

}