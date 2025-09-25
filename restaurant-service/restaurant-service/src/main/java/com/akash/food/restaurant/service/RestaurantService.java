package com.akash.food.restaurant.service;

import com.akash.food.restaurant.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
    RestaurantDTO updateRestaurant(String id, RestaurantDTO restaurantDTO);
    RestaurantDTO getRestaurantById(String id);
    List<RestaurantDTO> findRestaurantByName(String name);
    List<RestaurantDTO> getAllRestaurants();
    void deleteRestaurantById(String id);
}
