package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DISPLAY_ORDER")
	private BigDecimal displayOrder;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="FOOD_GENRE_NAME")
	private String foodGenreName;

	@Column(name="FOOT_GENRE_ID")
	private BigDecimal footGenreId;

	@Column(name="GENRE_LEVEL")
	private BigDecimal genreLevel;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="SUPERIOR_FOOT_GENRE_ID")
	private BigDecimal superiorFootGenreId;

	public FoodGenre() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFoodGenreName() {
		return this.foodGenreName;
	}

	public void setFoodGenreName(String foodGenreName) {
		this.foodGenreName = foodGenreName;
	}

	public BigDecimal getFootGenreId() {
		return this.footGenreId;
	}

	public void setFootGenreId(BigDecimal footGenreId) {
		this.footGenreId = footGenreId;
	}

	public BigDecimal getGenreLevel() {
		return this.genreLevel;
	}

	public void setGenreLevel(BigDecimal genreLevel) {
		this.genreLevel = genreLevel;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getSuperiorFootGenreId() {
		return this.superiorFootGenreId;
	}

	public void setSuperiorFootGenreId(BigDecimal superiorFootGenreId) {
		this.superiorFootGenreId = superiorFootGenreId;
	}

}