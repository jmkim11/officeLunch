package com.officelunch.recommendation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecommendationResponse create(@Valid @RequestBody RecommendationRequest request) {
        return recommendationService.create(request.getCompanyName(), request.getCategory());
    }

    @PostMapping("/{sessionId}/retry")
    public RecommendationResponse retry(@PathVariable Long sessionId) {
        return recommendationService.retry(sessionId);
    }

    @PostMapping("/{sessionId}/selection")
    public RecommendationResponse select(
            @PathVariable Long sessionId,
            @Valid @RequestBody SelectionRequest request) {
        return recommendationService.select(sessionId, request.getRestaurantId());
    }
}
