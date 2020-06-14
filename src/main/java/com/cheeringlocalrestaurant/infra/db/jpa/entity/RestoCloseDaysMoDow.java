package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
    @SequenceGenerator(allocationSize=1, name="RESTO_CLOSE_DAYS_MO_DOW_ID_GENERATOR", sequenceName="RESTO_CLOSE_DAYS_MO_DOW_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_CLOSE_DAYS_MO_DOW_ID_GENERATOR")
	private Long id;

	@Column(name="DAY_OF_WEEK")
	private Short dayOfWeek;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Column(name="WEEK_OF_MONTH")
	private Short weekOfMonth;

	public RestoCloseDaysMoDow() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getDayOfWeek() {
		return this.dayOfWeek;
	}

	public void setDayOfWeek(Short dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Short getWeekOfMonth() {
		return this.weekOfMonth;
	}

	public void setWeekOfMonth(Short weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

}