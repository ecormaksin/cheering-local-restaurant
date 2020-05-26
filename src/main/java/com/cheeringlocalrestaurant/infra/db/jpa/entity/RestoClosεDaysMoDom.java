package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
	@SequenceGenerator(name="RESTO_CLOSΕ_DAYS_MO_DOM_ID_GENERATOR", sequenceName="RESTO_CLOSΕ_DAYS_MO_DOM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_CLOSΕ_DAYS_MO_DOM_ID_GENERATOR")
	private Long id;

	@Column(name="DAY_OF_MONTH")
	private Integer dayOfMonth;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	public RestoClosεDaysMoDom() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDayOfMonth() {
		return this.dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}