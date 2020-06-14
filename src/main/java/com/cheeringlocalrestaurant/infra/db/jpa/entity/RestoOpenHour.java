package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
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
    @SequenceGenerator(allocationSize=1, name="RESTO_OPEN_HOURS_ID_GENERATOR", sequenceName="RESTO_OPEN_HOURS_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_OPEN_HOURS_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="END_TIME")
	private Date endTime;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_TIME")
	private Date startTime;

	public RestoOpenHour() {
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

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}