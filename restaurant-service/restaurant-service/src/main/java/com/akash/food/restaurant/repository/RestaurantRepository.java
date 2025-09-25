package com.akash.food.restaurant.repository;

import com.akash.food.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
}