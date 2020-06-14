package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RESTO_TELNOES database table.
 * 
 */
@Entity
@Table(name="RESTO_TELNOES")
@NamedQuery(name="RestoTelnoe.findAll", query="SELECT r FROM RestoTelnoe r")
public class RestoTelnoe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(allocationSize=1, name="RESTO_TELNOES_ID_GENERATOR", sequenceName="RESTO_TELNOES_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_TELNOES_ID_GENERATOR")
	private Long id;

	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Column(name="TEL_NO")
	private String telNo;

	public RestoTelnoe() {
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

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

}