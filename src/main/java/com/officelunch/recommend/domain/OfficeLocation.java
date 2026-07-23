package com.officelunch.recommend.domain;

public class OfficeLocation {
    private final String name;
    private final String roadAddress;
    private final double latitude;
    private final double longitude;

    public OfficeLocation(
        String name,
        String roadAddress,
        double latitude,
        double longitude
    ){
        this.name = name;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName(){
        return name;
    }

    public String getRoadAddress(){
        return roadAddress;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
}
