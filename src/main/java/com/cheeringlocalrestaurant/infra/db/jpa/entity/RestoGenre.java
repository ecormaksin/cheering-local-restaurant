package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RESTO_GENRES database table.
 * 
 */
@Entity
@Table(name="RESTO_GENRES")
@NamedQuery(name="RestoGenre.findAll", query="SELECT r FROM RestoGenre r")
public class RestoGenre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Column(name="FOOT_GENRE_ID")
	private Long footGenreId;

	public RestoGenre() {
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Long getFootGenreId() {
		return this.footGenreId;
	}

	public void setFootGenreId(Long footGenreId) {
		this.footGenreId = footGenreId;
	}

}