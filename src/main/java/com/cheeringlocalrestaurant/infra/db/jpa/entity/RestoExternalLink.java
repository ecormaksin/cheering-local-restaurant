package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
    @SequenceGenerator(allocationSize=1, name="RESTO_EXTERNAL_LINKS_ID_GENERATOR", sequenceName="RESTO_EXTERNAL_LINKS_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_EXTERNAL_LINKS_ID_GENERATOR")
	private Long id;

	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	private String url;

	public RestoExternalLink() {
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}