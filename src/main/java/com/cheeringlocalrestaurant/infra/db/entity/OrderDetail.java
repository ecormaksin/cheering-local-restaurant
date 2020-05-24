package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ORDER_DETAILS database table.
 * 
 */
@Entity
@Table(name="ORDER_DETAILS")
@NamedQuery(name="OrderDetail.findAll", query="SELECT o FROM OrderDetail o")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_DETAIL_ID")
	private long orderDetailId;

	@Column(name="MENU_ID")
	private BigDecimal menuId;

	@Column(name="MENU_NAME")
	private String menuName;

	@Column(name="ORDER_ID")
	private BigDecimal orderId;

	private BigDecimal quantity;

	@Column(name="TAX_INCLUDED_PRICE")
	private BigDecimal taxIncludedPrice;

	public OrderDetail() {
	}

	public long getOrderDetailId() {
		return this.orderDetailId;
	}

	public void setOrderDetailId(long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public BigDecimal getMenuId() {
		return this.menuId;
	}

	public void setMenuId(BigDecimal menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public BigDecimal getOrderId() {
		return this.orderId;
	}

	public void setOrderId(BigDecimal orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTaxIncludedPrice() {
		return this.taxIncludedPrice;
	}

	public void setTaxIncludedPrice(BigDecimal taxIncludedPrice) {
		this.taxIncludedPrice = taxIncludedPrice;
	}

}