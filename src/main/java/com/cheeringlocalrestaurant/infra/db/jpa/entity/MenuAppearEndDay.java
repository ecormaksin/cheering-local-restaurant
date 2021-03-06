package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the MENU_APPEAR_END_DAYS database table.
 * 
 */
@Entity
@Table(name="MENU_APPEAR_END_DAYS")
@NamedQuery(name="MenuAppearEndDay.findAll", query="SELECT m FROM MenuAppearEndDay m")
public class MenuAppearEndDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MENU_HISTORY_ID")
	private Long menuHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name="APPEAR_END_DATE")
	private Date appearEndDate;

	@Version
	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	public MenuAppearEndDay() {
	}

	public Long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(Long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

	public Date getAppearEndDate() {
		return this.appearEndDate;
	}

	public void setAppearEndDate(Date appearEndDate) {
		this.appearEndDate = appearEndDate;
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

}