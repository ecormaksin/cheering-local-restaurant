package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RESTO_MESSAGES database table.
 * 
 */
@Entity
@Table(name="RESTO_MESSAGES")
@NamedQuery(name="RestoMessage.findAll", query="SELECT r FROM RestoMessage r")
public class RestoMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESTAURANT_HISTORY_ID")
	private long restaurantHistoryId;

	@Column(name="MESSAGE_FROM_RESTAURANT")
	private String messageFromRestaurant;

	public RestoMessage() {
	}

	public long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public String getMessageFromRestaurant() {
		return this.messageFromRestaurant;
	}

	public void setMessageFromRestaurant(String messageFromRestaurant) {
		this.messageFromRestaurant = messageFromRestaurant;
	}

}