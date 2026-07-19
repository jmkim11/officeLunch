package com.officelunch.recommendation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.officelunch.recommendation.domain.RecommendationSession;
import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import com.officelunch.restaurant.domain.WaitRisk;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryRecommendationSessionRepositoryTest {

    @Test
    void 저장한_추천_세션을_ID로_조회한다() {
        InMemoryRecommendationSessionRepository repository =
            new InMemoryRecommendationSessionRepository();
        RecommendationSession session = new RecommendationSession(List.of(restaurant()));

        repository.save(session);

        assertTrue(repository.findById(session.getId()).isPresent());
        assertEquals(session, repository.findById(session.getId()).get());
    }

    private Restaurant restaurant() {
        return new Restaurant(
            1L,
            "김치찌개집",
            FoodCategory.KOREAN,
            "서울특별시 강남구 테헤란로",
            37.5000,
            127.0300,
            5,
            10000,
            WaitRisk.LOW,
            RestaurantStatus.ACTIVE,
            "test:1"
        );
    }
}
