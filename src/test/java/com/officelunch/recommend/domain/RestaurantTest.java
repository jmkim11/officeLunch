package com.officelunch.recommend.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class RestaurantTest {
    @Test
    void 식당_객체가_제대로_생성된다(){
        // Given & When
        Restaurant restaurant = new Restaurant(
            1L,
            "정민이네 김치찌개",
            "서울특별시 구로구 구로동", 
            0.0, 
            0.0, 
            FoodCategory.EUROPE
        );

        // Then
        assertAll(
            () -> assertEquals(1L, restaurant.getId()),
            () -> assertEquals(
                "정민이네 김치찌개",
                restaurant.getName()
            ),
            () -> assertEquals(
                "서울특별시 구로구 구로동",
                restaurant.getAddress()
            ),
            () -> assertEquals(0.0, restaurant.getLatitude()),
            () -> assertEquals(0.0, restaurant.getLongitude()),
            () -> assertEquals(
                FoodCategory.EUROPE,
                restaurant.getCategory()
            )
        );
    }

    @Test
    void 식당_정보가_제대로_기입이_되지_않으면_오류를_반환한다(){
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Restaurant(
                null, 
                null, 
                null, 
                0, 
                0, 
                null
            )
        );

        assertEquals(
            "식당정보를 제대로 입력해주세요",
            exception.getMessage()
        );
    }
}
