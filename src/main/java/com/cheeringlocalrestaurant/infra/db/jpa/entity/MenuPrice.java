package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MENU_HISTORY_ID")
	private long menuHistoryId;

	@Column(name="TAX_INCLUDED_PRICE")
	private BigDecimal taxIncludedPrice;

	public MenuPrice() {
	}

	public long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

	public BigDecimal getTaxIncludedPrice() {
		return this.taxIncludedPrice;
	}

	public void setTaxIncludedPrice(BigDecimal taxIncludedPrice) {
		this.taxIncludedPrice = taxIncludedPrice;
	}

}