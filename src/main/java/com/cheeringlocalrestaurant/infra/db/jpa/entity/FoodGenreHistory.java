package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the FOOD_GENRE_HISTORIES database table.
 * 
 */
@Entity
@Table(name="FOOD_GENRE_HISTORIES")
@NamedQuery(name="FoodGenreHistory.findAll", query="SELECT f FROM FoodGenreHistory f")
public class FoodGenreHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FOOT_GENRE_HISTORY_ID")
	private Long footGenreHistoryId;

	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="FOOD_GENRE_NAME")
	private String foodGenreName;

	@Column(name="FOOT_GENRE_ID")
	private Long footGenreId;

	@Column(name="GENRE_LEVEL")
	private Integer genreLevel;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="SUPERIOR_FOOT_GENRE_ID")
	private Integer superiorFootGenreId;

	public FoodGenreHistory() {
	}

	public Long getFootGenreHistoryId() {
		return this.footGenreHistoryId;
	}

	public void setFootGenreHistoryId(Long footGenreHistoryId) {
		this.footGenreHistoryId = footGenreHistoryId;
	}

	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
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

	public Long getFootGenreId() {
		return this.footGenreId;
	}

	public void setFootGenreId(Long footGenreId) {
		this.footGenreId = footGenreId;
	}

	public Integer getGenreLevel() {
		return this.genreLevel;
	}

	public void setGenreLevel(Integer genreLevel) {
		this.genreLevel = genreLevel;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getSuperiorFootGenreId() {
		return this.superiorFootGenreId;
	}

	public void setSuperiorFootGenreId(Integer superiorFootGenreId) {
		this.superiorFootGenreId = superiorFootGenreId;
	}

}