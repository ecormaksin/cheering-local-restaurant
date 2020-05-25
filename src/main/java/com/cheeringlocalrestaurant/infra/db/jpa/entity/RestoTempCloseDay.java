package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RESTO_TEMP_CLOSE_DAYS database table.
 * 
 */
@Entity
@Table(name="RESTO_TEMP_CLOSE_DAYS")
@NamedQuery(name="RestoTempCloseDay.findAll", query="SELECT r FROM RestoTempCloseDay r")
public class RestoTempCloseDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="CLOSE_DAY")
	private Date closeDay;

	@Column(name="RESTAURANT_ID")
	private BigDecimal restaurantId;

	public RestoTempCloseDay() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCloseDay() {
		return this.closeDay;
	}

	public void setCloseDay(Date closeDay) {
		this.closeDay = closeDay;
	}

	public BigDecimal getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(BigDecimal restaurantId) {
		this.restaurantId = restaurantId;
	}

}