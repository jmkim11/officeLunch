/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.officelunch;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author 김정민
 */
public class CompanySearchServiceTest {

    @Test
    void 검색어의_앞뒤_공백을_제거한다(){
        FakePlaceSearchPort port = new FakePlaceSearchPort();
        CompanySearchService service = new CompanySearchService(port);

        service.search("   버킷플레이스   ");

        assertEquals(
            "버킷플레이스",
            port.getReceivedQuery()
        );
    }

    @Test
    void 빈_검색어는_거부한다(){
        FakePlaceSearchPort port = new FakePlaceSearchPort();
        CompanySearchService service = new CompanySearchService(port);

        assertThrows(
            IllegalArgumentException.class,
            () -> service.search("   ")
        );
    }

    @Test
    void 한_글자_검색어는_거부한다(){
        FakePlaceSearchPort port = new FakePlaceSearchPort();
        CompanySearchService service = new CompanySearchService(port);

        assertThrows(
            IllegalArgumentException.class,
            () -> service.search(" 힝 ")
        );
    }

    @Test
    void 정상_검색_결과를_반환한다(){
        CompanySearchResult expected = new CompanySearchResult(
            "kakao:1", 
            "버킷플레이스", 
            "서울특별시 서초구 서초대로74길 4", 
            "서울특별시 서초구 서초동", 
            37.4979, 
            127.0276, 
            PlaceSource.KAKAO
        );

        PlaceSearchPort fakeport = query -> List.of(expected);
        CompanySearchService service = new CompanySearchService(fakeport);

        List<CompanySearchResult> results = 
            service.search("버킷플레이스");
        
        assertEquals(1, results.size());
        assertEquals("버킷플레이스", results.get(0).getName());
    }

    private static class FakePlaceSearchPort implements PlaceSearchPort {

        private String receivedQuery;

        @Override
        public List<CompanySearchResult> search(String query){
            this.receivedQuery = query;
            return List.of();
        }

        public String getReceivedQuery(){
            return receivedQuery;
        }
    }

}
