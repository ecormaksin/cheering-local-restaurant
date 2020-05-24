package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RESTO_ADDRESSES database table.
 * 
 */
@Entity
@Table(name="RESTO_ADDRESSES")
@NamedQuery(name="RestoAddress.findAll", query="SELECT r FROM RestoAddress r")
public class RestoAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESTAURANT_HISTORY_ID")
	private long restaurantHistoryId;

	@Column(name="AFTER_TOWN_NAME")
	private String afterTownName;

	@Column(name="CITY_NAME")
	private String cityName;

	@Column(name="PREFECTURE_NAME")
	private String prefectureName;

	@Column(name="TOWN_NAME")
	private String townName;

	@Column(name="ZIP_CODE")
	private String zipCode;

	public RestoAddress() {
	}

	public long getRestaurantHistoryId() {
		return this.restaurantHistoryId;
	}

	public void setRestaurantHistoryId(long restaurantHistoryId) {
		this.restaurantHistoryId = restaurantHistoryId;
	}

	public String getAfterTownName() {
		return this.afterTownName;
	}

	public void setAfterTownName(String afterTownName) {
		this.afterTownName = afterTownName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPrefectureName() {
		return this.prefectureName;
	}

	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}

	public String getTownName() {
		return this.townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}