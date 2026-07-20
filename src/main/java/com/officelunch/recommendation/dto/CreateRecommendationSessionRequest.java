package com.officelunch.recommendation.dto;

import com.officelunch.restaurant.domain.FoodCategory;
import jakarta.validation.constraints.NotNull;

public class CreateRecommendationSessionRequest {
    @NotNull(message = "음식 카테고리는 필수입니다.")
    private FoodCategory category;

    public CreateRecommendationSessionRequest() {
    }

    public CreateRecommendationSessionRequest(FoodCategory category) {
        this.category = category;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }
}
