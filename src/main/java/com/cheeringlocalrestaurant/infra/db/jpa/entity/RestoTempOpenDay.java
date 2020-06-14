package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
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
    @SequenceGenerator(allocationSize=1, name="RESTO_TEMP_OPEN_DAYS_ID_GENERATOR", sequenceName="RESTO_TEMP_OPEN_DAYS_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_TEMP_OPEN_DAYS_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Temporal(TemporalType.DATE)
	@Column(name="OPEN_DAY")
	private Date openDay;

	@Column(name="RESTAURANT_ID")
	private Long restaurantId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	public RestoTempOpenDay() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public Long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}