package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_PHOTOS database table.
 * 
 */
@Entity
@Table(name="RESTO_PHOTOS")
@NamedQuery(name="RestoPhoto.findAll", query="SELECT r FROM RestoPhoto r")
public class RestoPhoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DISPLAY_ORDER")
	private BigDecimal displayOrder;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	public RestoPhoto() {
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

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(BigDecimal restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}