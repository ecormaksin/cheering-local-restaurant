package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the MENU_HISTORIES database table.
 * 
 */
@Entity
@Table(name="MENU_HISTORIES")
@NamedQuery(name="MenuHistory.findAll", query="SELECT m FROM MenuHistory m")
public class MenuHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="MENU_HISTORY_ID")
	private long menuHistoryId;

	@Temporal(TemporalType.DATE)
	@Column(name="APPEAR_START_DATE")
	private Date appearStartDate;

	@Column(name="MENU_ID")
	private BigDecimal menuId;

	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	public MenuHistory() {
	}

	public long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

	public Date getAppearStartDate() {
		return this.appearStartDate;
	}

	public void setAppearStartDate(Date appearStartDate) {
		this.appearStartDate = appearStartDate;
	}

	public BigDecimal getMenuId() {
		return this.menuId;
	}

	public void setMenuId(BigDecimal menuId) {
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

}