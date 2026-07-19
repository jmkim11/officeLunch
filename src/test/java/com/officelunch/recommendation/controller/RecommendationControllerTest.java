package com.officelunch.recommendation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.officelunch.common.error.GlobalExceptionHandler;
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

    @BeforeEach
    void setUp() {
        RecommendationService service = new RecommendationService(
            new InMemoryRestaurantRepository(List.of(restaurant())),
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
