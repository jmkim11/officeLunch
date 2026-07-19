/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.officelunch.recommendation.domain;

import java.util.Set;
import java.util.List;
import java.util.HashSet;

/**
 *
 * @author 김정민
 */
public class RecommendationSession {
    private final List<Restaurant> candidates;
    private final Set<Long> recommendedRestaurantIds = new HashSet<>();

    private Long selectedRestaurantId;
    private RecommendationStatus status;

    public RecommendationSession(List<Restaurant> candidates){
        if (candidates == null || candidates.isEmpty()){
            throw new IllegalArgumentException("추천 후보 식당이 필요합니다.");
        }

        this.candidates = List.copyOf(candidates);
        this.status = RecommendationStatus.IN_PROGRESS;
    }

    public Restaurant recommend(){
        // TODO
        // 어떻게 할 것인가?
        if (status == RecommendationStatus.SELECTED){
            throw new IllegalStateException("이미 식당을 선택한 추천 세션입니다.");

        }

        if (status == RecommendationStatus.EXHAUSTED){
            throw new IllegalStateException("더 이상 추천할 식당이 없습니다.");
        }

        for (Restaurant restaurant: candidates){
            if (restaurant.getRestaurantStatus() != RestaurantStatus.ACTIVE){
                continue;
            }

            if (recommendedRestaurantIds.contains(restaurant.getId())){
                continue;
            }

            recommendedRestaurantIds.add(restaurant.getId());
            return restaurant;
        }

        status = RecommendationStatus.EXHAUSTED;
        throw new IllegalStateException("더 이상 추천할 식당이 없습니다.");
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

    public RecommendationStatus getStatus(){
        return status;
    }

    public Long getSelectedRestaurantId(){
        return selectedRestaurantId;
    }
}
