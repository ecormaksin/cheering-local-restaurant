package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RESTO_TEMP_OPEN_DAYS database table.
 * 
 */
@Entity
@Table(name="RESTO_TEMP_OPEN_DAYS")
@NamedQuery(name="RestoTempOpenDay.findAll", query="SELECT r FROM RestoTempOpenDay r")
public class RestoTempOpenDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Temporal(TemporalType.DATE)
	@Column(name="OPEN_DAY")
	private Date openDay;

	@Column(name="RESTAURANT_ID")
	private BigDecimal restaurantId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	public RestoTempOpenDay() {
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

	public Date getOpenDay() {
		return this.openDay;
	}

	public void setOpenDay(Date openDay) {
		this.openDay = openDay;
	}

	public BigDecimal getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(BigDecimal restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}