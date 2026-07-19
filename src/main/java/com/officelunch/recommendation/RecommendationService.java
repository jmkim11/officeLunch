package com.officelunch.recommendation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecommendationService {
    private final RecommendationSessionRepository sessionRepository;
    private final RecommendationHistoryRepository historyRepository;
    private final RestaurantRepository restaurantRepository;

    public RecommendationService(
            RecommendationSessionRepository sessionRepository,
            RecommendationHistoryRepository historyRepository,
            RestaurantRepository restaurantRepository) {
        this.sessionRepository = sessionRepository;
        this.historyRepository = historyRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public RecommendationResponse create(CreateRecommendationRequest request) {
        RecommendationSession session = sessionRepository.save(
                new RecommendationSession(request.getCompanyName().trim(), request.getCategory().trim().toUpperCase())
        );
        return recommendNext(session);
    }

    @Transactional
    public RecommendationResponse retry(Long sessionId) {
        RecommendationSession session = getSession(sessionId);
        session.requireActive();
        return recommendNext(session);
    }

    @Transactional
    public void select(Long sessionId, Long restaurantId) {
        RecommendationSession session = getSession(sessionId);
        session.requireActive();

        RecommendationHistory history = historyRepository
                .findBySessionIdAndRestaurantId(sessionId, restaurantId)
                .orElseThrow(() -> new RecommendationException(
                        "RESTAURANT_NOT_RECOMMENDED",
                        "추천받지 않은 식당은 선택할 수 없습니다."
                ));

        history.select();
        session.complete();
    }

    private RecommendationResponse recommendNext(RecommendationSession session) {
        List<Long> excludedIds = historyRepository.findBySessionId(session.getId()).stream()
                .map(history -> history.getRestaurant().getId())
                .toList();

        List<Restaurant> candidates = excludedIds.isEmpty()
                ? restaurantRepository.findByCategory(session.getCategory())
                : restaurantRepository.findByCategoryAndIdNotIn(session.getCategory(), excludedIds);

        if (candidates.isEmpty()) {
            session.exhaust();
            throw new RecommendationException("NO_RESTAURANT_CANDIDATE", "추천 가능한 식당이 없습니다.");
        }

        Restaurant selected = candidates.get(0);
        historyRepository.save(new RecommendationHistory(session, selected));
        return new RecommendationResponse(session.getId(), selected, session.getStatus());
    }

    private RecommendationSession getSession(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RecommendationException(
                        "RECOMMENDATION_NOT_FOUND",
                        "추천 세션을 찾을 수 없습니다."
                ));
    }
}
