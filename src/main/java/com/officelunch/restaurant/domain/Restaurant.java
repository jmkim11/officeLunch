package com.officelunch.restaurant.domain;

public class Restaurant {
    private final Long id;
    private final String name;
    private final FoodCategory category;
    private final String address;
    private final double latitude;
    private final double longitude;
    private final WaitRisk waitRisk;
    private final RestaurantStatus restaurantStatus;

    public Restaurant(
        Long id,
        String name,
        FoodCategory category,
        String address,
        double latitude,
        double longitude,
        WaitRisk waitRisk,
        RestaurantStatus restaurantStatus
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waitRisk = waitRisk;
        this.restaurantStatus = restaurantStatus;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public WaitRisk getWaitRisk() {
        return waitRisk;
    }

    public RestaurantStatus getRestaurantStatus() {
        return restaurantStatus;
    }
}
