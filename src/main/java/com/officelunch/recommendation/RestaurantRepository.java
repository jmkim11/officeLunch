package com.officelunch.recommendation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCompanyNameAndCategoryOrderByIdAsc(String companyName, String category);
}
