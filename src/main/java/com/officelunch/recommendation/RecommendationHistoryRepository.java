package com.officelunch.recommendation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationHistoryRepository extends JpaRepository<RecommendationHistory, Long> {
    List<RecommendationHistory> findBySession_IdOrderByRecommendedAtAsc(Long sessionId);
    boolean existsBySession_IdAndRestaurant_Id(Long sessionId, Long restaurantId);
}
