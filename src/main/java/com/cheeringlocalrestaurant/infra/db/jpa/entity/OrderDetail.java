package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
    @SequenceGenerator(name="ORDER_DETAILS_ORDERDETAILID_GENERATOR", sequenceName="ORDER_DETAILS_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_DETAILS_ORDERDETAILID_GENERATOR")
	@Column(name="ORDER_DETAIL_ID")
	private Long orderDetailId;

	@Column(name="MENU_ID")
	private Long menuId;

	@Column(name="MENU_NAME")
	private String menuName;

	@Column(name="ORDER_ID")
	private Long orderId;

	private Integer quantity;

	@Column(name="TAX_INCLUDED_PRICE")
	private Integer taxIncludedPrice;

	public OrderDetail() {
	}

	public Long getOrderDetailId() {
		return this.orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTaxIncludedPrice() {
		return this.taxIncludedPrice;
	}

	public void setTaxIncludedPrice(Integer taxIncludedPrice) {
		this.taxIncludedPrice = taxIncludedPrice;
	}

}