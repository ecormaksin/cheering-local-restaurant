package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MENU_NAMES database table.
 * 
 */
@Entity
@Table(name="MENU_NAMES")
@NamedQuery(name="MenuName.findAll", query="SELECT m FROM MenuName m")
public class MenuName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MENU_HISTORY_ID")
	private Long menuHistoryId;

	@Column(name="MENU_NAME")
	private String menuName;

	public MenuName() {
	}

	public Long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(Long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}