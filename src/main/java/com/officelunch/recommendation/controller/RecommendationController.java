package com.officelunch.recommendation.controller;

import com.officelunch.recommendation.dto.CreateRecommendationSessionRequest;
import com.officelunch.recommendation.dto.RecommendationResponse;
import com.officelunch.recommendation.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations/sessions")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecommendationResponse createSession(
        @Valid @RequestBody CreateRecommendationSessionRequest request
    ) {
        return recommendationService.createSession(request.getCategory());
    }
}
