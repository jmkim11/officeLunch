package com.officelunch.recommendation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer implements CommandLineRunner {
    private final RestaurantRepository restaurantRepository;

    public DemoDataInitializer(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) {
        if (restaurantRepository.count() > 0) {
            return;
        }

        restaurantRepository.save(new Restaurant("demo-1", "포스타입", "정민식당", "KOREAN"));
        restaurantRepository.save(new Restaurant("demo-2", "포스타입", "포스김치", "KOREAN"));
        restaurantRepository.save(new Restaurant("demo-3", "포스타입", "오늘의 파스타", "WESTERN"));
    }
}
