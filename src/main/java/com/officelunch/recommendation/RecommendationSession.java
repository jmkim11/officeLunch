package com.officelunch.recommendation;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_session")
public class RecommendationSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected RecommendationSession() {}

    public RecommendationSession(String companyName, String category) {
        this.companyName = companyName;
        this.category = category;
        this.status = RecommendationStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public void complete() {
        requireActive();
        this.status = RecommendationStatus.COMPLETED;
    }

    public void exhaust() {
        requireActive();
        this.status = RecommendationStatus.EXHAUSTED;
    }

    public void requireActive() {
        if (status != RecommendationStatus.ACTIVE) {
            throw new InvalidRecommendationStateException("활성 상태의 추천 세션만 변경할 수 있습니다.");
        }
    }

    public Long getId() { return id; }
    public String getCompanyName() { return companyName; }
    public String getCategory() { return category; }
    public RecommendationStatus getStatus() { return status; }
}
