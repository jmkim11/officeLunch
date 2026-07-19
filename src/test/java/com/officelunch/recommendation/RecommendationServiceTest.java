package com.officelunch.recommendation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RecommendationServiceTest {
    @Autowired RecommendationService recommendationService;
    @Autowired RestaurantRepository restaurantRepository;
    @Autowired RecommendationHistoryRepository historyRepository;

    @BeforeEach
    void setUp() {
        restaurantRepository.save(new Restaurant("restaurant-1", "오픈AI 코리아", "한식당", "KOREAN"));
        restaurantRepository.save(new Restaurant("restaurant-2", "오픈AI 코리아", "김치집", "KOREAN"));
    }

    @Test
    void retryExcludesPreviouslyRecommendedRestaurant() {
        RecommendationResponse first = recommendationService.create("오픈AI 코리아", "korean");
        RecommendationResponse second = recommendationService.retry(first.getSessionId());

        assertThat(second.getRestaurantId()).isNotEqualTo(first.getRestaurantId());
        assertThat(historyRepository.findBySession_IdOrderByRecommendedAtAsc(first.getSessionId())).hasSize(2);
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

    @Test
    void failsWhenAllCandidatesAreExhausted() {
        RecommendationResponse first = recommendationService.create("오픈AI 코리아", "KOREAN");
        recommendationService.retry(first.getSessionId());

        assertThatThrownBy(() -> recommendationService.retry(first.getSessionId()))
                .isInstanceOf(NoRestaurantCandidateException.class);
    }
}
