/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.officelunch.recommendation.domain;

/**
 *
 * @author 김정민
 */
// 식당 정보를 파악하려면
// 식당 이름
// 위치 정보
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
    ){
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waitRisk = waitRisk;
        this.restaurantStatus = restaurantStatus;
    }

    public Long getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }

    public FoodCategory getCategory(){
        return category;
    }

    public String getAddress(){
        return address;
    }

    public WaitRisk getWaitRisk(){
        return waitRisk;
    }

    // 위도, 경도를 반환할 필요가 있나..?
    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public RestaurantStatus getRestaurantStatus(){
        return restaurantStatus;
    }

}
