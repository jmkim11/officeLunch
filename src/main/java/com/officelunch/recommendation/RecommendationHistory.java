package com.officelunch.recommendation;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_history", uniqueConstraints = @UniqueConstraint(name = "uk_session_restaurant", columnNames = {"session_id", "restaurant_id"}))
public class RecommendationHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private RecommendationSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private boolean selected;

    @Column(nullable = false)
    private LocalDateTime recommendedAt;

    protected RecommendationHistory() {}

    public RecommendationHistory(RecommendationSession session, Restaurant restaurant) {
        this.session = session;
        this.restaurant = restaurant;
        this.selected = false;
        this.recommendedAt = LocalDateTime.now();
    }

    public void select() { this.selected = true; }
    public Long getId() { return id; }
    public RecommendationSession getSession() { return session; }
    public Restaurant getRestaurant() { return restaurant; }
    public boolean isSelected() { return selected; }
    public LocalDateTime getRecommendedAt() { return recommendedAt; }
}
