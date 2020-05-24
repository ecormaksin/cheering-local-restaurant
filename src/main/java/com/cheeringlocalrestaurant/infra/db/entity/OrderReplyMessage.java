package com.cheeringlocalrestaurant.infra.db.entity;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	private long orderId;

	@Column(name="\"MESSAGE\"")
	private String message;

	public OrderReplyMessage() {
	}

	public long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}