package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * The persistent class for the RESTO_HISTORIES database table.
 * 
 */
@Entity
@Table(name="RESTO_HISTORIES")
@NamedQuery(name="RestoHistory.findAll", query="SELECT r FROM RestoHistory r")
public class RestoHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESTO_HISTORIES_RESTAURANTHISTORYID_GENERATOR", sequenceName="RESTO_HISTORIES_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTO_HISTORIES_RESTAURANTHISTORYID_GENERATOR")
	@Column(name="RESTAURANT_HISTORY_ID")
	private Long restaurantHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate = java.sql.Timestamp.valueOf(LocalDateTime.of(9999, 12, 31, 0, 0));

	@Version
	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	@Column(name="RESTAURANT_ID")
	private Long restaurantId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate = java.sql.Timestamp.valueOf(LocalDateTime.of(1900, 1, 1, 0, 0));

	public RestoHistory() {
	}

	public Long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(Long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Long getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}