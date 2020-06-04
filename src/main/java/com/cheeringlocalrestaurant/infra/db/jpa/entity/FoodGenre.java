package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FOOD_GENRES database table.
 * 
 */
@Entity
@Table(name="FOOD_GENRES")
@NamedQuery(name="FoodGenre.findAll", query="SELECT f FROM FoodGenre f")
public class FoodGenre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FOOT_GENRE_ID")
	private Long footGenreId;

	public FoodGenre() {
	}

	public Long getFootGenreId() {
		return this.footGenreId;
	}

	public void setFootGenreId(Long footGenreId) {
		this.footGenreId = footGenreId;
	}

}