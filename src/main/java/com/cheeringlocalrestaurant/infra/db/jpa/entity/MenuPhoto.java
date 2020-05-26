package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


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
	@SequenceGenerator(name="MENU_PHOTOS_ID_GENERATOR", sequenceName="MENU_PHOTOS_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MENU_PHOTOS_ID_GENERATOR")
	private Long id;

	@Column(name="DISPLAY_ORDER")
	private Integer displayOrder;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="MENU_HISTORY_ID")
	private Long menuHistoryId;

	public MenuPhoto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(Long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

}