package com.cheeringlocalrestaurant.infra.db.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the RESTO_ORDER_AVAILABILITY database table.
 * 
 */
@Entity
@Table(name = "RESTO_ORDER_AVAILABILITY")
@NamedQuery(name = "RestoOrderAvailability.findAll", query = "SELECT r FROM RestoOrderAvailability r")
public class RestoOrderAvailability implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESTAURANT_HISTORY_ID")
    private Long restaurantHistoryId;

    public RestoOrderAvailability() {
    }

    public Long getRestaurantHistoryId() {
        return this.restaurantHistoryId;
    }

    public void setRestaurantHistoryId(Long restaurantHistoryId) {
        this.restaurantHistoryId = restaurantHistoryId;
    }

}