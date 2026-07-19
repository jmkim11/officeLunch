package com.officelunch.recommendation.service;

import com.officelunch.common.error.BusinessException;
import com.officelunch.common.error.ErrorCode;
import com.officelunch.recommendation.domain.RecommendationSession;
import com.officelunch.recommendation.dto.RecommendationResponse;
import com.officelunch.recommendation.repository.RecommendationSessionRepository;
import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    private final RestaurantRepository restaurantRepository;
    private final RecommendationSessionRepository sessionRepository;

    public RecommendationService(
        RestaurantRepository restaurantRepository,
        RecommendationSessionRepository sessionRepository
    ) {
        this.restaurantRepository = restaurantRepository;
        this.sessionRepository = sessionRepository;
    }

    public RecommendationResponse createSession(FoodCategory category) {
        List<Restaurant> candidates = new ArrayList<>(
            restaurantRepository.findActiveByCategory(category)
        );
        Collections.shuffle(candidates);

        RecommendationSession session = new RecommendationSession(candidates);
        Restaurant firstRecommendation = session.recommend();
        sessionRepository.save(session);

        return RecommendationResponse.from(session, firstRecommendation);
    }

    public RecommendationResponse recommendNext(String sessionId) {
        RecommendationSession session = findSession(sessionId);
        Restaurant nextRecommendation = session.recommend();
        sessionRepository.save(session);

        return RecommendationResponse.from(session, nextRecommendation);
    }

    private RecommendationSession findSession(String sessionId) {
        return sessionRepository.findById(sessionId)
            .orElseThrow(() -> new BusinessException(
                ErrorCode.RECOMMENDATION_SESSION_NOT_FOUND
            ));
    }
}
