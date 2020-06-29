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
	@SequenceGenerator(allocationSize=1, name="RESTO_TEMP_OPEN_DAYS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_TEMP_OPEN_DAYS_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE_TIME")
	private Date endDateTime;

	@Column(name="RESTAURANT_ID")
	private Long restaurantId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE_TIME")
	private Date startDateTime;

	public RestoTempOpenDay() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEndDateTime() {
		return this.endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getStartDateTime() {
		return this.startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

}