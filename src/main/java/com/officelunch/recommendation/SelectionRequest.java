package com.officelunch.recommendation;

import jakarta.validation.constraints.NotNull;

public class SelectionRequest {
    @NotNull
    private Long restaurantId;

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
}
