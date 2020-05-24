package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_EXTERNAL_LINKS database table.
 * 
 */
@Entity
@Table(name="RESTO_EXTERNAL_LINKS")
@NamedQuery(name="RestoExternalLink.findAll", query="SELECT r FROM RestoExternalLink r")
public class RestoExternalLink implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DISPLAY_ORDER")
	private BigDecimal displayOrder;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	private String url;

	public RestoExternalLink() {
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

	public BigDecimal getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(BigDecimal restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}