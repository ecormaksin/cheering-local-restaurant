package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ORDER_REPLY_MESSAGES database table.
 * 
 */
@Entity
@Table(name="ORDER_REPLY_MESSAGES")
@NamedQuery(name="OrderReplyMessage.findAll", query="SELECT o FROM OrderReplyMessage o")
public class OrderReplyMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORDER_ID")
	private Long orderId;

	@Column(name="\"MESSAGE\"")
	private String message;

	public OrderReplyMessage() {
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}