package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the RESTO_SEARCH_KEYWORDS database table.
 * 
 */
@Entity
@Table(name = "RESTO_SEARCH_KEYWORDS")
@NamedQuery(name = "RestoSearchKeyword.findAll", query = "SELECT r FROM RestoSearchKeyword r")
public class RestoSearchKeyword implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RESTO_SEARCH_KEYWORDS_ID_GENERATOR", sequenceName = "RESTO_SEARCH_KEYWORDS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESTO_SEARCH_KEYWORDS_ID_GENERATOR")
    private Long id;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "RESTAURANT_HISTORY_ID")
    private Long restaurantHistoryId;

    @Column(name = "SEARCH_KEY_WORD")
    private String searchKeyWord;

    public RestoSearchKeyword() {
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

    public Long getRestaurantHistoryId() {
        return this.restaurantHistoryId;
    }

    public void setRestaurantHistoryId(Long restaurantHistoryId) {
        this.restaurantHistoryId = restaurantHistoryId;
    }

    public String getSearchKeyWord() {
        return this.searchKeyWord;
    }

    public void setSearchKeyWord(String searchKeyWord) {
        this.searchKeyWord = searchKeyWord;
    }

}