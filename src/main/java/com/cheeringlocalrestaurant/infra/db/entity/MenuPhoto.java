package com.cheeringlocalrestaurant.infra.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MENU_PHOTOS database table.
 * 
 */
@Entity
@Table(name="MENU_PHOTOS")
@NamedQuery(name="MenuPhoto.findAll", query="SELECT m FROM MenuPhoto m")
public class MenuPhoto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="DISPLAY_ORDER")
	private BigDecimal displayOrder;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="MENU_HISTORY_ID")
	private BigDecimal menuHistoryId;

	public MenuPhoto() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(BigDecimal menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

}