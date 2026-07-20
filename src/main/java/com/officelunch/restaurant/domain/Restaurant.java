package com.officelunch.restaurant.domain;

public class Restaurant {
    private final Long id;
    private final String name;
    private final FoodCategory category;
    private final String address;
    private final double latitude;
    private final double longitude;
    private final int walkingMinutes;
    private final int averagePrice;
    private final WaitRisk waitRisk;
    private final RestaurantStatus restaurantStatus;
    private final String externalPlaceId;

    public Restaurant(
        Long id,
        String name,
        FoodCategory category,
        String address,
        double latitude,
        double longitude,
        int walkingMinutes,
        int averagePrice,
        WaitRisk waitRisk,
        RestaurantStatus restaurantStatus,
        String externalPlaceId
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.walkingMinutes = walkingMinutes;
        this.averagePrice = averagePrice;
        this.waitRisk = waitRisk;
        this.restaurantStatus = restaurantStatus;
        this.externalPlaceId = externalPlaceId;
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

    public int getWalkingMinutes() {
        return walkingMinutes;
    }

    public int getAveragePrice() {
        return averagePrice;
    }

    public WaitRisk getWaitRisk() {
        return waitRisk;
    }

    public RestaurantStatus getRestaurantStatus() {
        return restaurantStatus;
    }

    public String getExternalPlaceId() {
        return externalPlaceId;
    }
}
