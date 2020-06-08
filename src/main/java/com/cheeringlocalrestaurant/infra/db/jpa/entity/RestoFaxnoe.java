package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
    @SequenceGenerator(name="RESTO_FAXNOES_ID_GENERATOR", sequenceName="RESTO_FAXNOES_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_FAXNOES_ID_GENERATOR")
	private Long id;

	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	@Column(name="FAX_NO")
	private String faxNo;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	public RestoFaxnoe() {
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

	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

}