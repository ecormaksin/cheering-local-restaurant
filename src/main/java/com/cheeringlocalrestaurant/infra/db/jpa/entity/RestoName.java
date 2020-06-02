package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the RESTO_NAMES database table.
 * 
 */
@Entity
@Table(name = "RESTO_NAMES")
@NamedQuery(name = "RestoName.findAll", query = "SELECT r FROM RestoName r")
public class RestoName implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESTAURANT_HISTORY_ID")
    private Long restaurantHistoryId;

    @Column(name = "RESTAURANT_NAME")
    private String restaurantName;

    public RestoName() {
    }

    public Long getRestaurantHistoryId() {
        return this.restaurantHistoryId;
    }

    public void setRestaurantHistoryId(Long restaurantHistoryId) {
        this.restaurantHistoryId = restaurantHistoryId;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

}