package com.officelunch.restaurant.repository;

import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> findActiveByCategory(FoodCategory category);
}
