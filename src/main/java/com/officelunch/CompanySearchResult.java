/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.officelunch;

/**
 *
 * @author 김정민
 */
public class CompanySearchResult {
    private final String placeId;
    private final String name;
    private final String roadAddress;
    private final String jibunAddress;
    private final double latitude;
    private final double longitude;
    private final PlaceSource source;

    public CompanySearchResult(
        String placeId,
        String name,
        String roadAddress,
        String jibunAddress,
        double latitude,
        double longitude,
        PlaceSource source
    ){
        this.placeId = placeId;
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.source = source;
    }

    public String getPlaceId(){
        return placeId;
    }

    public String getName(){
        return name;
    }

    public String getRoadAddress(){
        return roadAddress;
    }

    public String getJibunAddress(){
        return jibunAddress;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public PlaceSource getSource(){
        return source;
    }

}
