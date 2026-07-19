package com.officelunch.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCategoryAndIdNotIn(String category, List<Long> excludedIds);
    List<Restaurant> findByCategory(String category);
}
