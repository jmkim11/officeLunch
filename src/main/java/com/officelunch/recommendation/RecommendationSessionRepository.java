package com.officelunch.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationSessionRepository extends JpaRepository<RecommendationSession, Long> {
}
