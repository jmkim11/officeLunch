package com.officelunch.recommendation;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public RecommendationResponse create(String companyName, String category) {
        RecommendationSession session = sessionRepository.save(
                new RecommendationSession(companyName.trim(), category.trim().toUpperCase())
        );
        return recommendNext(session);
    }

    @Transactional
    public RecommendationResponse retry(Long sessionId) {
        RecommendationSession session = getActiveSession(sessionId);
        return recommendNext(session);
    }

    @Transactional
    public RecommendationResponse select(Long sessionId, Long restaurantId) {
        RecommendationSession session = getActiveSession(sessionId);
        RecommendationHistory history = historyRepository
                .findBySessionIdOrderByRecommendedAtAsc(sessionId)
                .stream()
                .filter(item -> item.getRestaurantId().equals(restaurantId))
                .findFirst()
                .orElseThrow(() -> new RestaurantNotRecommendedException("추천받지 않은 식당은 선택할 수 없습니다."));

        history.select();
        session.complete();
        return new RecommendationResponse(session.getId(), history.getRestaurantId(), history.getRestaurantName(), session.getStatus());
    }

    private RecommendationResponse recommendNext(RecommendationSession session) {
        List<Restaurant> candidates = restaurantRepository
                .findByCompanyNameAndCategoryOrderByIdAsc(session.getCompanyName(), session.getCategory())
                .stream()
                .filter(restaurant -> !historyRepository.existsBySessionIdAndRestaurantId(session.getId(), restaurant.getId()))
                .toList();

        if (candidates.isEmpty()) {
            session.exhaust();
            throw new NoRestaurantCandidateException("추천 가능한 식당이 더 이상 없습니다.");
        }

        Restaurant selected = candidates.get(0);
        historyRepository.save(new RecommendationHistory(session.getId(), selected));
        return new RecommendationResponse(session.getId(), selected.getId(), selected.getName(), session.getStatus());
    }

    private RecommendationSession getActiveSession(Long sessionId) {
        RecommendationSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RecommendationNotFoundException("추천 세션을 찾을 수 없습니다."));
        session.requireActive();
        return session;
    }
}
