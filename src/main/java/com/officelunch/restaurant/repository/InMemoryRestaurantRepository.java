package com.officelunch.restaurant.repository;

import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import com.officelunch.restaurant.domain.WaitRisk;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryRestaurantRepository implements RestaurantRepository {
    private final List<Restaurant> restaurants;

    public InMemoryRestaurantRepository() {
        this(createSampleRestaurants());
    }

    public InMemoryRestaurantRepository(List<Restaurant> restaurants) {
        this.restaurants = List.copyOf(restaurants);
    }

    @Override
    public List<Restaurant> findActiveByCategory(FoodCategory category) {
        return restaurants.stream()
            .filter(restaurant -> restaurant.getCategory() == category)
            .filter(restaurant -> restaurant.getRestaurantStatus() == RestaurantStatus.ACTIVE)
            .toList();
    }

    private static List<Restaurant> createSampleRestaurants() {
        return List.of(
            restaurant(1L, "직장인 김치찌개", FoodCategory.KOREAN, 5, 10000, WaitRisk.LOW),
            restaurant(2L, "오늘의 백반", FoodCategory.KOREAN, 7, 11000, WaitRisk.MEDIUM),
            restaurant(3L, "스시 하루", FoodCategory.JAPANESE, 6, 15000, WaitRisk.MEDIUM),
            restaurant(4L, "우동 한 그릇", FoodCategory.JAPANESE, 4, 9000, WaitRisk.LOW),
            restaurant(5L, "차이나 키친", FoodCategory.CHINESE, 8, 12000, WaitRisk.HIGH),
            restaurant(6L, "마라 점심", FoodCategory.CHINESE, 9, 13000, WaitRisk.MEDIUM),
            restaurant(7L, "오피스 파스타", FoodCategory.WESTERN, 7, 16000, WaitRisk.MEDIUM),
            restaurant(8L, "샌드위치 데스크", FoodCategory.WESTERN, 3, 8500, WaitRisk.LOW)
        );
    }

    private static Restaurant restaurant(
        Long id,
        String name,
        FoodCategory category,
        int walkingMinutes,
        int averagePrice,
        WaitRisk waitRisk
    ) {
        return new Restaurant(
            id,
            name,
            category,
            "서울특별시 강남구 테헤란로",
            37.5000 + (id * 0.0001),
            127.0300 + (id * 0.0001),
            walkingMinutes,
            averagePrice,
            waitRisk,
            RestaurantStatus.ACTIVE,
            "sample:" + id
        );
    }
}
