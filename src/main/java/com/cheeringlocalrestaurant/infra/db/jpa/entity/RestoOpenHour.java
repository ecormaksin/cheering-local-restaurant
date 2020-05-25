package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RESTO_OPEN_HOURS database table.
 * 
 */
@Entity
@Table(name="RESTO_OPEN_HOURS")
@NamedQuery(name="RestoOpenHour.findAll", query="SELECT r FROM RestoOpenHour r")
public class RestoOpenHour implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	public RestoOpenHour() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(BigDecimal restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}