package com.akash.food.restaurant.mapper;

import com.akash.food.restaurant.dto.RestaurantDTO;
import com.akash.food.restaurant.entities.Restaurant;

public class RestaurantMapper {

    public static RestaurantDTO toDTO(Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getPictures(),
                restaurant.isOpen(),
                restaurant.getOpenTime(),
                restaurant.getCloseTime(),
                restaurant.getAboutRestaurant()
        );
    }

    public static Restaurant toEntity(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.id()); // optional
        restaurant.setName(dto.name());
        restaurant.setAddress(dto.address());
        restaurant.setPhone(dto.phone());
        restaurant.setPictures(dto.pictures());
        restaurant.setOpen(dto.open());
        restaurant.setOpenTime(dto.openTime());
        restaurant.setCloseTime(dto.closeTime());
        restaurant.setAboutRestaurant(dto.aboutRestaurant());
        return restaurant;
    }
}
