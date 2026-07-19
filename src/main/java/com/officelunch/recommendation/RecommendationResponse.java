package com.officelunch.recommendation;

public class RecommendationResponse {
    private final Long sessionId;
    private final Long restaurantId;
    private final String restaurantName;
    private final String category;
    private final RecommendationStatus status;

    public RecommendationResponse(Long sessionId, Restaurant restaurant, RecommendationStatus status) {
        this.sessionId = sessionId;
        this.restaurantId = restaurant.getId();
        this.restaurantName = restaurant.getName();
        this.category = restaurant.getCategory();
        this.status = status;
    }

    public Long getSessionId() { return sessionId; }
    public Long getRestaurantId() { return restaurantId; }
    public String getRestaurantName() { return restaurantName; }
    public String getCategory() { return category; }
    public RecommendationStatus getStatus() { return status; }
}
