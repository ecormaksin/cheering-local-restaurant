package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MENU_PRICES database table.
 * 
 */
@Entity
@Table(name="MENU_PRICES")
@NamedQuery(name="MenuPrice.findAll", query="SELECT m FROM MenuPrice m")
public class MenuPrice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MENU_HISTORY_ID")
	private Long menuHistoryId;

	@Column(name="TAX_INCLUDED_PRICE")
	private Integer taxIncludedPrice;

	public MenuPrice() {
	}

	public Long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(Long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

	public Integer getTaxIncludedPrice() {
		return this.taxIncludedPrice;
	}

	public void setTaxIncludedPrice(Integer taxIncludedPrice) {
		this.taxIncludedPrice = taxIncludedPrice;
	}

}