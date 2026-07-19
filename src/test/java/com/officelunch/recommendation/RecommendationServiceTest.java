package com.officelunch.recommendation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(RecommendationService.class)
class RecommendationServiceTest {
    @Autowired RecommendationService recommendationService;
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired RecommendationHistoryRepository historyRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository.save(new Restaurant("오픈AI 코리아", "KOREAN", "한식당"));
        restaurantRepository.save(new Restaurant("오픈AI 코리아", "KOREAN", "김치집"));
    }

    @Test
    void retryExcludesPreviouslyRecommendedRestaurant() {
        RecommendationResponse first = recommendationService.create("오픈AI 코리아", "korean");
        RecommendationResponse second = recommendationService.retry(first.getSessionId());

        assertThat(second.getRestaurantId()).isNotEqualTo(first.getRestaurantId());
        assertThat(historyRepository.findBySessionIdOrderByRecommendedAtAsc(first.getSessionId())).hasSize(2);
    }

    @Test
    void cannotSelectRestaurantThatWasNotRecommended() {
        RecommendationResponse response = recommendationService.create("오픈AI 코리아", "KOREAN");

        assertThatThrownBy(() -> recommendationService.select(response.getSessionId(), 999L))
                .isInstanceOf(RestaurantNotRecommendedException.class);
    }

    @Test
    void completedSessionCannotRetry() {
        RecommendationResponse response = recommendationService.create("오픈AI 코리아", "KOREAN");
        recommendationService.select(response.getSessionId(), response.getRestaurantId());

        assertThatThrownBy(() -> recommendationService.retry(response.getSessionId()))
                .isInstanceOf(InvalidRecommendationStateException.class);
    }
}
