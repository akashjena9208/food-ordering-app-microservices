package com.akash.food.restaurant.service.impl;

import com.akash.food.restaurant.dto.RestaurantDTO;
import com.akash.food.restaurant.entities.Restaurant;
import com.akash.food.restaurant.exception.ResourceNotFoundException;
import com.akash.food.restaurant.mapper.RestaurantMapper;
import com.akash.food.restaurant.repository.RestaurantRepository;
import com.akash.food.restaurant.service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        log.info("Creating restaurant: {}", restaurantDTO.name());
        Restaurant restaurant = RestaurantMapper.toEntity(restaurantDTO);
        Restaurant saved = restaurantRepository.save(restaurant);
        log.info("Restaurant created with ID: {}", saved.getId());
        return RestaurantMapper.toDTO(saved);
    }

//    @Override
//    @CachePut(value = "restaurants", key = "#id")
//    public RestaurantDTO updateRestaurant(String id, RestaurantDTO restaurantDTO) {
//        log.info("Updating restaurant with ID: {}", id);
//        Restaurant existing = restaurantRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + id));
//
//        existing.setName(restaurantDTO.name());
//        existing.setAddress(restaurantDTO.address());
//        existing.setPhone(restaurantDTO.phone());
//        existing.setPictures(restaurantDTO.pictures());
//        existing.setOpen(restaurantDTO.open());
//        existing.setOpenTime(restaurantDTO.openTime());
//        existing.setCloseTime(restaurantDTO.closeTime());
//        existing.setAboutRestaurant(restaurantDTO.aboutRestaurant());
//
//        Restaurant updated = restaurantRepository.save(existing);
//        log.info("Restaurant updated with ID: {}", updated.getId());
//        return RestaurantMapper.toDTO(updated);
//    }


    @Override
    @Transactional
    @CachePut(value = "restaurants", key = "#id")
    public RestaurantDTO updateRestaurant(String id, RestaurantDTO restaurantDTO) {
        log.info("Updating restaurant with ID: {}", id);

        Restaurant existing = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + id));

        existing.setName(restaurantDTO.name());
        existing.setAddress(restaurantDTO.address());
        existing.setPhone(restaurantDTO.phone());

        // Update pictures carefully to avoid LOB issues
        existing.getPictures().clear();
        existing.getPictures().addAll(restaurantDTO.pictures());

        existing.setOpen(restaurantDTO.open());
        existing.setOpenTime(restaurantDTO.openTime());
        existing.setCloseTime(restaurantDTO.closeTime());
        existing.setAboutRestaurant(restaurantDTO.aboutRestaurant());

        Restaurant updated = restaurantRepository.save(existing);
        log.info("Restaurant updated with ID: {}", updated.getId());
        return RestaurantMapper.toDTO(updated);
    }


    @Override
    @Cacheable(value = "restaurants", key = "#id")
    public RestaurantDTO getRestaurantById(String id) {
        log.info("Fetching restaurant with ID: {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + id));
        return RestaurantMapper.toDTO(restaurant);
    }
//
//    @Override
//    @Cacheable(value = "restaurantsByName", key = "#name")
//    public List<RestaurantDTO> findRestaurantByName(String name) {
//        log.info("Searching restaurants by name: {}", name);
//        return restaurantRepository.findByNameContainingIgnoreCase(name)
//                .stream()
//                .map(RestaurantMapper::toDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    @Transactional
    @Cacheable(value = "restaurantsByName", key = "#name")
    public List<RestaurantDTO> findRestaurantByName(String name) {
        log.info("Searching restaurants by name: {}", name);
        return restaurantRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(RestaurantMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Cacheable(value = "allRestaurants")
    public List<RestaurantDTO> getAllRestaurants() {
        log.info("Fetching all restaurants");
        return restaurantRepository.findAll()
                .stream()
                .map(RestaurantMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "restaurants", key = "#id")
    public void deleteRestaurantById(String id) {
        log.info("Deleting restaurant with ID: {}", id);
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with ID: " + id);
        }
        restaurantRepository.deleteById(id);
        log.info("Restaurant deleted with ID: {}", id);
    }
}
