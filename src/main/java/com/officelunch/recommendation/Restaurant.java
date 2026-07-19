package com.officelunch.recommendation;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(name = "uk_restaurant_external_id", columnNames = "external_id"))
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    protected Restaurant() {}

    public Restaurant(String externalId, String name, String category) {
        this.externalId = externalId;
        this.name = name;
        this.category = category;
    }

    public Long getId() { return id; }
    public String getExternalId() { return externalId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
}
