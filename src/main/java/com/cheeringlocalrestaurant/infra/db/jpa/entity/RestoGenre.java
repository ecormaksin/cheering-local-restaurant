package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESTAURANT_HISTORY_ID")
	private long restaurantHistoryId;

	@Column(name="FOOT_GENRE_ID")
	private BigDecimal footGenreId;

	public RestoGenre() {
	}

	public long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public BigDecimal getFootGenreId() {
		return this.footGenreId;
	}

	public void setFootGenreId(BigDecimal footGenreId) {
		this.footGenreId = footGenreId;
	}

}