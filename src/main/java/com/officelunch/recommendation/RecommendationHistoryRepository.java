package com.officelunch.recommendation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationHistoryRepository extends JpaRepository<RecommendationHistory, Long> {
    List<RecommendationHistory> findBySessionIdOrderByRecommendedAtAsc(Long sessionId);
    boolean existsBySessionIdAndRestaurantId(Long sessionId, Long restaurantId);
}
