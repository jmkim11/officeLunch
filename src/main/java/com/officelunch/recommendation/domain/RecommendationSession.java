package com.officelunch.recommendation.domain;

import com.officelunch.common.error.BusinessException;
import com.officelunch.common.error.ErrorCode;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RecommendationSession {
    private final String id;
    private final List<Restaurant> candidates;
    private final Set<Long> recommendedRestaurantIds = new HashSet<>();

    private Long selectedRestaurantId;
    private RecommendationStatus status;

    public RecommendationSession(List<Restaurant> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            throw new BusinessException(ErrorCode.RECOMMENDATION_CANDIDATE_NOT_FOUND);
        }

        this.id = UUID.randomUUID().toString();
        this.candidates = List.copyOf(candidates);
        this.status = RecommendationStatus.IN_PROGRESS;
    }

    public Restaurant recommend() {
        if (status == RecommendationStatus.SELECTED) {
            throw new BusinessException(ErrorCode.RECOMMENDATION_ALREADY_SELECTED);
        }

        if (status == RecommendationStatus.EXHAUSTED) {
            throw new BusinessException(ErrorCode.RECOMMENDATION_EXHAUSTED);
        }

        for (Restaurant restaurant : candidates) {
            if (restaurant.getRestaurantStatus() != RestaurantStatus.ACTIVE) {
                continue;
            }

            if (recommendedRestaurantIds.contains(restaurant.getId())) {
                continue;
            }

            recommendedRestaurantIds.add(restaurant.getId());
            return restaurant;
        }

        status = RecommendationStatus.EXHAUSTED;
        throw new BusinessException(ErrorCode.RECOMMENDATION_EXHAUSTED);
    }

    public void select(Long restaurantId) {
          if (status == RecommendationStatus.SELECTED) {
              throw new IllegalStateException("이미 식당을 선택한 추천 세션입니다.");
          }

          boolean exists = candidates.stream()
              .anyMatch(restaurant -> restaurant.getId().equals(restaurantId));

          if (!exists) {
              throw new IllegalArgumentException("후보에 없는 식당은 선택할 수 없습니다.");
          }

          this.selectedRestaurantId = restaurantId;
          this.status = RecommendationStatus.SELECTED;
    }

    public RecommendationStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public Long getSelectedRestaurantId() {
        return selectedRestaurantId;
    }
}
