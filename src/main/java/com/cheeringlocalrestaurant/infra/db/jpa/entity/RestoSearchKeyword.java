package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RESTO_SEARCH_KEYWORDS database table.
 * 
 */
@Entity
@Table(name="RESTO_SEARCH_KEYWORDS")
@NamedQuery(name="RestoSearchKeyword.findAll", query="SELECT r FROM RestoSearchKeyword r")
public class RestoSearchKeyword implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DISPLAY_ORDER")
	private BigDecimal displayOrder;

	@Column(name="RESTAURANT_HISTORY_ID")
	private BigDecimal restaurantHistoryId;

	@Column(name="SEARCH_KEY_WORD")
	private String searchKeyWord;

	public RestoSearchKeyword() {
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

	public String getSearchKeyWord() {
		return this.searchKeyWord;
	}

	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}

}