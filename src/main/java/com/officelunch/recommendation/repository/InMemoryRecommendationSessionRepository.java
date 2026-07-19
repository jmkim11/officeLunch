package com.officelunch.recommendation.repository;

import com.officelunch.recommendation.domain.RecommendationSession;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRecommendationSessionRepository implements RecommendationSessionRepository {
    private final Map<String, RecommendationSession> sessions = new ConcurrentHashMap<>();

    @Override
    public RecommendationSession save(RecommendationSession session) {
        sessions.put(session.getId(), session);
        return session;
    }

    @Override
    public Optional<RecommendationSession> findById(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }
}
