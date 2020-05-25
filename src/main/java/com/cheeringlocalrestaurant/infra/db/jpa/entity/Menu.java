package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the MENU database table.
 * 
 */
@Entity
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MENU_ID")
	private long menuId;

	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	@Column(name="RESTAURANT_ID")
	private BigDecimal restaurantId;

	public Menu() {
	}

	public long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public Timestamp getRegisteredTimestamp() {
		return this.registeredTimestamp;
	}

	public void setRegisteredTimestamp(Timestamp registeredTimestamp) {
		this.registeredTimestamp = registeredTimestamp;
	}

	public String getRemoteIpAddress() {
		return this.remoteIpAddress;
	}

	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	public BigDecimal getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(BigDecimal restaurantId) {
		this.restaurantId = restaurantId;
	}

}