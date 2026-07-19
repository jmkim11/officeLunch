package com.officelunch.recommendation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    void 다음_추천시_이전에_추천한_식당을_제외한다() {
        RecommendationService service = new RecommendationService(
            new InMemoryRestaurantRepository(List.of(
                restaurant(1L, "김치찌개집"),
                restaurant(2L, "된장찌개집")
            )),
            new InMemoryRecommendationSessionRepository()
        );
        RecommendationResponse first = service.createSession(FoodCategory.KOREAN);

        RecommendationResponse second = service.recommendNext(first.getSessionId());

        assertNotEquals(first.getRestaurant().getId(), second.getRestaurant().getId());
    }

    @Test
    void 존재하지_않는_세션의_다음_추천은_실패한다() {
        RecommendationService service = new RecommendationService(
            new InMemoryRestaurantRepository(List.of(restaurant())),
            new InMemoryRecommendationSessionRepository()
        );

        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> service.recommendNext("missing-session")
        );

        assertEquals(ErrorCode.RECOMMENDATION_SESSION_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 모든_후보를_추천하면_다음_추천에_실패한다() {
        RecommendationService service = new RecommendationService(
            new InMemoryRestaurantRepository(List.of(restaurant())),
            new InMemoryRecommendationSessionRepository()
        );
        RecommendationResponse first = service.createSession(FoodCategory.KOREAN);

        BusinessException exception = assertThrows(
            BusinessException.class,
            () -> service.recommendNext(first.getSessionId())
        );

        assertEquals(ErrorCode.RECOMMENDATION_EXHAUSTED, exception.getErrorCode());
    }

    private Restaurant restaurant() {
        return restaurant(1L, "김치찌개집");
    }

    private Restaurant restaurant(Long id, String name) {
        return new Restaurant(
            id,
            name,
            FoodCategory.KOREAN,
            "서울특별시 강남구 테헤란로",
            37.5000,
            127.0300,
            5,
            10000,
            WaitRisk.LOW,
            RestaurantStatus.ACTIVE,
            "test:" + id
        );
    }
}
