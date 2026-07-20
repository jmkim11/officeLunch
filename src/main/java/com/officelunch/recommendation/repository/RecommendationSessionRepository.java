package com.officelunch.recommendation.repository;

import com.officelunch.recommendation.domain.RecommendationSession;
import java.util.Optional;

public interface RecommendationSessionRepository {
    RecommendationSession save(RecommendationSession session);

    Optional<RecommendationSession> findById(String sessionId);
}
