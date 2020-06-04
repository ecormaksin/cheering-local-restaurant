package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MENU_MEMOS database table.
 * 
 */
@Entity
@Table(name="MENU_MEMOS")
@NamedQuery(name="MenuMemo.findAll", query="SELECT m FROM MenuMemo m")
public class MenuMemo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MENU_HISTORY_ID")
	private Long menuHistoryId;

	private String memo;

	public MenuMemo() {
	}

	public Long getMenuHistoryId() {
		return this.menuHistoryId;
	}

	public void setMenuHistoryId(Long menuHistoryId) {
		this.menuHistoryId = menuHistoryId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}