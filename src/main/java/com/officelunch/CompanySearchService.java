/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.officelunch;

import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
 * @author 김정민
 */

@Service
public class CompanySearchService {
    private final PlaceSearchPort placeSearchPort;

    public CompanySearchService(PlaceSearchPort placeSearchPort){
        this.placeSearchPort = placeSearchPort;
    }

    public List<CompanySearchResult> search(String query){
        if (query == null){
            throw new IllegalArgumentException("검색어는 필수입니다.");
        }
        
        String normalizedQuery = query.trim();

        if (normalizedQuery.length() < 2){
            throw new IllegalArgumentException("검색어는 2자 이상이어야 합니다.");

        }
        
        return placeSearchPort.search(normalizedQuery);
    }

}
