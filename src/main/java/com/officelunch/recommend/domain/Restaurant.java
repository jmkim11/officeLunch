package com.officelunch.recommend.domain;


public class Restaurant {
    private final Long id;
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;
    private final FoodCategory category;

    public Restaurant(
        Long id, 
        String name, 
        String address, 
        double latitude, 
        double longitude, 
        FoodCategory category
    ){
        
        if (id == null || name == null || address == null){
            throw new IllegalArgumentException("식당정보를 제대로 입력해주세요");
        }
        
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public FoodCategory getCategory(){
        return category;
    }
}
