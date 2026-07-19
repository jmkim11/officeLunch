package com.officelunch.restaurant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import com.officelunch.restaurant.domain.WaitRisk;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryRestaurantRepositoryTest {

    @Test
    void 선택한_카테고리의_운영중_식당만_조회한다() {
        InMemoryRestaurantRepository repository = new InMemoryRestaurantRepository(List.of(
            restaurant(1L, FoodCategory.KOREAN, RestaurantStatus.ACTIVE),
            restaurant(2L, FoodCategory.KOREAN, RestaurantStatus.CLOSED),
            restaurant(3L, FoodCategory.JAPANESE, RestaurantStatus.ACTIVE)
        ));

        List<Restaurant> restaurants = repository.findActiveByCategory(FoodCategory.KOREAN);

        assertEquals(1, restaurants.size());
        assertEquals(1L, restaurants.get(0).getId());
    }

    private Restaurant restaurant(
        Long id,
        FoodCategory category,
        RestaurantStatus status
    ) {
        return new Restaurant(
            id,
            "테스트 식당 " + id,
            category,
            "서울특별시 강남구 테헤란로",
            37.5000,
            127.0300,
            5,
            10000,
            WaitRisk.LOW,
            status,
            "test:" + id
        );
    }
}
