package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RESTO_NAMES database table.
 * 
 */
@Entity
@Table(name="RESTO_NAMES")
@NamedQuery(name="RestoName.findAll", query="SELECT r FROM RestoName r")
public class RestoName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESTAURANT_HISTORY_ID")
	private long restaurantHistoryId;

	@Column(name="RESTAURANT_NAME")
	private String restaurantName;

	public RestoName() {
	}

	public long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public String getRestaurantName() {
		return this.restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

}