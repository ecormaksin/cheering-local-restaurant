package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	public RestoPhoto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}