package com.officelunch.recommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.officelunch.common.error.BusinessException;
import com.officelunch.common.error.ErrorCode;
import com.officelunch.recommendation.dto.RecommendationResponse;
import com.officelunch.recommendation.repository.InMemoryRecommendationSessionRepository;
import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import com.officelunch.restaurant.domain.WaitRisk;
import com.officelunch.restaurant.repository.InMemoryRestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecommendationServiceTest {

    @Test
    void 세션_생성시_첫_추천과_IN_PROGRESS_상태를_반환한다() {
        InMemoryRecommendationSessionRepository sessionRepository =
            new InMemoryRecommendationSessionRepository();
        RecommendationService service = new RecommendationService(
            new InMemoryRestaurantRepository(List.of(restaurant())),
            sessionRepository
        );

        RecommendationResponse response = service.createSession(FoodCategory.KOREAN);

        assertEquals("김치찌개집", response.getRestaurant().getName());
        assertEquals("IN_PROGRESS", response.getStatus().name());
        assertTrue(sessionRepository.findById(response.getSessionId()).isPresent());
    }

    @Test
    void 추천_후보가_없으면_세션_생성에_실패한다() {
        RecommendationService service = new RecommendationService(
            new InMemoryRestaurantRepository(List.of()),
            new InMemoryRecommendationSessionRepository()
        );

        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> service.createSession(FoodCategory.KOREAN)
        );

        assertEquals(ErrorCode.RECOMMENDATION_CANDIDATE_NOT_FOUND, exception.getErrorCode());
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
