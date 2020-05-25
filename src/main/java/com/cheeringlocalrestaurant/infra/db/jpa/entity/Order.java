package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the ORDERS database table.
 * 
 */
@Entity
@Table(name="ORDERS")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	private long orderId;

	@Column(name="CUSTOMER_NAME")
	private String customerName;

	@Temporal(TemporalType.DATE)
	@Column(name="DESIRED_RECEIPT_DATETIME")
	private Date desiredReceiptDatetime;

	@Column(name="MAIL_ADDRESS")
	private String mailAddress;

	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	@Column(name="RESTAURANT_ID")
	private BigDecimal restaurantId;

	@Column(name="TEL_NO")
	private String telNo;

	public Order() {
	}

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getDesiredReceiptDatetime() {
		return this.desiredReceiptDatetime;
	}

	public void setDesiredReceiptDatetime(Date desiredReceiptDatetime) {
		this.desiredReceiptDatetime = desiredReceiptDatetime;
	}

	public String getMailAddress() {
		return this.mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

}