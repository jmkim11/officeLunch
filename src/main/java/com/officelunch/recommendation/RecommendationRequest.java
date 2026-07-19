package com.officelunch.recommendation;

import jakarta.validation.constraints.NotBlank;

public class RecommendationRequest {
    @NotBlank
    private String companyName;

    @NotBlank
    private String category;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
