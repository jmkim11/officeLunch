package com.officelunch.recommendation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.officelunch.common.error.GlobalExceptionHandler;
import com.officelunch.recommendation.dto.RecommendationResponse;
import com.officelunch.recommendation.repository.InMemoryRecommendationSessionRepository;
import com.officelunch.recommendation.service.RecommendationService;
import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import com.officelunch.restaurant.domain.WaitRisk;
import com.officelunch.restaurant.repository.InMemoryRestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RecommendationControllerTest {
    private MockMvc mockMvc;
    private RecommendationService service;

    @BeforeEach
    void setUp() {
        configure(List.of(restaurant(1L, "김치찌개집")));
    }

    private void configure(List<Restaurant> restaurants) {
        service = new RecommendationService(
            new InMemoryRestaurantRepository(restaurants),
            new InMemoryRecommendationSessionRepository()
        );
        RecommendationController controller = new RecommendationController(service);

        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void 추천_세션_생성시_201과_첫_추천을_반환한다() throws Exception {
        mockMvc.perform(post("/api/recommendations/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\":\"KOREAN\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.sessionId").isNotEmpty())
            .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
            .andExpect(jsonPath("$.restaurant.name").value("김치찌개집"));
    }

    @Test
    void 음식_카테고리가_없으면_400을_반환한다() throws Exception {
        mockMvc.perform(post("/api/recommendations/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
            .andExpect(jsonPath("$.message").value("음식 카테고리는 필수입니다."));
    }

    @Test
    void 읽을_수_없는_JSON이면_400을_반환한다() throws Exception {
        mockMvc.perform(post("/api/recommendations/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"category\":"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
    }

    @Test
    void 다음_추천시_200과_새로운_식당을_반환한다() throws Exception {
        configure(List.of(
            restaurant(1L, "김치찌개집"),
            restaurant(2L, "된장찌개집")
        ));
        RecommendationResponse first = service.createSession(FoodCategory.KOREAN);
        long expectedRestaurantId = first.getRestaurant().getId() == 1L ? 2L : 1L;

        mockMvc.perform(post(
                "/api/recommendations/sessions/{sessionId}/next",
                first.getSessionId()
            ))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sessionId").value(first.getSessionId()))
            .andExpect(jsonPath("$.restaurant.id").value(expectedRestaurantId));
    }

    @Test
    void 존재하지_않는_세션의_다음_추천은_404를_반환한다() throws Exception {
        mockMvc.perform(post(
                "/api/recommendations/sessions/{sessionId}/next",
                "missing-session"
            ))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("RECOMMENDATION_SESSION_NOT_FOUND"));
    }

    @Test
    void 추천_후보를_모두_사용하면_409를_반환한다() throws Exception {
        RecommendationResponse first = service.createSession(FoodCategory.KOREAN);

        mockMvc.perform(post(
                "/api/recommendations/sessions/{sessionId}/next",
                first.getSessionId()
            ))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.code").value("RECOMMENDATION_EXHAUSTED"));
    }

    @Test
    void 추천받은_식당을_선택하면_200과_SELECTED를_반환한다() throws Exception {
        RecommendationResponse recommendation = service.createSession(FoodCategory.KOREAN);

        mockMvc.perform(post(
                "/api/recommendations/sessions/{sessionId}/selection",
                recommendation.getSessionId()
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"restaurantId\":" + recommendation.getRestaurant().getId() + "}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("SELECTED"))
            .andExpect(jsonPath("$.restaurant.id")
                .value(recommendation.getRestaurant().getId()));
    }

    @Test
    void 추천받지_않은_식당을_선택하면_400을_반환한다() throws Exception {
        configure(List.of(
            restaurant(1L, "김치찌개집"),
            restaurant(2L, "된장찌개집")
        ));
        RecommendationResponse recommendation = service.createSession(FoodCategory.KOREAN);
        long notRecommendedId = recommendation.getRestaurant().getId() == 1L ? 2L : 1L;

        mockMvc.perform(post(
                "/api/recommendations/sessions/{sessionId}/selection",
                recommendation.getSessionId()
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"restaurantId\":" + notRecommendedId + "}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("RESTAURANT_NOT_RECOMMENDED"));
    }

    @Test
    void 식당_ID가_0이면_400을_반환한다() throws Exception {
        RecommendationResponse recommendation = service.createSession(FoodCategory.KOREAN);

        mockMvc.perform(post(
                "/api/recommendations/sessions/{sessionId}/selection",
                recommendation.getSessionId()
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"restaurantId\":0}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
            .andExpect(jsonPath("$.message").value("식당 ID는 1 이상이어야 합니다."));
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
