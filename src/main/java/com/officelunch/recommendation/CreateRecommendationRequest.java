package com.officelunch.recommendation;

import jakarta.validation.constraints.NotBlank;

public class CreateRecommendationRequest {
    @NotBlank
    private String companyName;

    @NotBlank
    private String category;

    public String getCompanyName() { return companyName; }
    public String getCategory() { return category; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setCategory(String category) { this.category = category; }
}
