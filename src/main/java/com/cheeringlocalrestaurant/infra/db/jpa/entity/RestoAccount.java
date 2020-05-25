package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the RESTO_ACCOUNTS database table.
 * 
 */
@Entity
@Table(name="RESTO_ACCOUNTS")
@NamedQuery(name="RestoAccount.findAll", query="SELECT r FROM RestoAccount r")
public class RestoAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESTAURANT_ID")
	private long restaurantId;

	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	@Column(name="USER_ID")
	private BigDecimal userId;

	public RestoAccount() {
	}

	public long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
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

	public BigDecimal getUserId() {
		return this.userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

}