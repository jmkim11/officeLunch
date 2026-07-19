package com.officelunch.restaurant.repository;

import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    List<Restaurant> findActiveByCategory(FoodCategory category);

    Optional<Restaurant> findById(Long restaurantId);
}
