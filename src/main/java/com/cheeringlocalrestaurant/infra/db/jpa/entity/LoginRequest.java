package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * The persistent class for the LOGIN_REQUESTS database table.
 * 
 */
@Entity
@Table(name="LOGIN_REQUESTS")
@NamedQuery(name="LoginRequest.findAll", query="SELECT l FROM LoginRequest l")
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="LOGIN_REQUESTS_ID_GENERATOR", sequenceName="LOGIN_REQUESTS_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOGIN_REQUESTS_ID_GENERATOR")
	private Long id;

	@Column(name="ACCESS_TOKEN")
	private String accessToken;

	@Version
	@Column(name="REGISTERED_TIMESTAMP")
	private Timestamp registeredTimestamp;

	@Column(name="REMOTE_IP_ADDRESS")
	private String remoteIpAddress;

	@Column(name="TOKEN_EXPIRATION_DATETIME")
	private Timestamp tokenExpirationDatetime;

	@Column(name="USER_ID")
	private Long userId;

	public LoginRequest() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}