package com.officelunch.recommendation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SelectRestaurantRequest {
    @NotNull(message = "식당 ID는 필수입니다.")
    @Positive(message = "식당 ID는 1 이상이어야 합니다.")
    private Long restaurantId;

    public SelectRestaurantRequest() {
    }

    public SelectRestaurantRequest(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
