package com.officelunch.recommendation;

public class RecommendationResponse {
    private final Long sessionId;
    private final Long restaurantId;
    private final String restaurantName;
    private final RecommendationStatus status;

    public RecommendationResponse(Long sessionId, Long restaurantId, String restaurantName, RecommendationStatus status) {
        this.sessionId = sessionId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.status = status;
    }

    public Long getSessionId() { return sessionId; }
    public Long getRestaurantId() { return restaurantId; }
    public String getRestaurantName() { return restaurantName; }
    public RecommendationStatus getStatus() { return status; }
}
