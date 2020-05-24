package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the RESTO_LOGIN_REQUESTS database table.
 * 
 */
@Entity
@Table(name="RESTO_LOGIN_REQUESTS")
@NamedQuery(name="RestoLoginRequest.findAll", query="SELECT r FROM RestoLoginRequest r")
public class RestoLoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="ACCESS_TOKEN")
	private String accessToken;

	@Column(name="MAIL_ADDRESS")
	private String mailAddress;

	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	@Column(name="TOKEN_EXPIRATION_DATETIME")
	private Timestamp tokenExpirationDatetime;

	public RestoLoginRequest() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

	public Timestamp getTokenExpirationDatetime() {
		return this.tokenExpirationDatetime;
	}

	public void setTokenExpirationDatetime(Timestamp tokenExpirationDatetime) {
		this.tokenExpirationDatetime = tokenExpirationDatetime;
	}

}