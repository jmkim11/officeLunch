package com.officelunch.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RecommendationHistoryRepository extends JpaRepository<RecommendationHistory, Long> {
    List<RecommendationHistory> findBySessionId(Long sessionId);
    Optional<RecommendationHistory> findBySessionIdAndRestaurantId(Long sessionId, Long restaurantId);
}
