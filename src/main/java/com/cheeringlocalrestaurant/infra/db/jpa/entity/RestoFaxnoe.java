package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_FAXNOES database table.
 * 
 */
@Entity
@Table(name="RESTO_FAXNOES")
@NamedQuery(name="RestoFaxnoe.findAll", query="SELECT r FROM RestoFaxnoe r")
public class RestoFaxnoe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DISPLAY_ORDER")
	private BigDecimal displayOrder;

	@Column(name="FAX_NO")
	private String faxNo;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	public RestoFaxnoe() {
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

	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public BigDecimal getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(BigDecimal restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}