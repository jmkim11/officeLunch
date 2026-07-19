package com.officelunch.recommendation.dto;

import com.officelunch.recommendation.domain.RecommendationSession;
import com.officelunch.recommendation.domain.RecommendationStatus;
import com.officelunch.restaurant.domain.Restaurant;

public class RecommendationResponse {
    private final String sessionId;
    private final RecommendationStatus status;
    private final RestaurantResponse restaurant;

    public RecommendationResponse(
        String sessionId,
        RecommendationStatus status,
        RestaurantResponse restaurant
    ) {
        this.sessionId = sessionId;
        this.status = status;
        this.restaurant = restaurant;
    }

    public static RecommendationResponse from(
        RecommendationSession session,
        Restaurant restaurant
    ) {
        return new RecommendationResponse(
            session.getId(),
            session.getStatus(),
            RestaurantResponse.from(restaurant)
        );
    }

    public String getSessionId() {
        return sessionId;
    }

    public RecommendationStatus getStatus() {
        return status;
    }

    public RestaurantResponse getRestaurant() {
        return restaurant;
    }
}
