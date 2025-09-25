package com.akash.food.restaurant.controller;

import com.akash.food.restaurant.dto.RestaurantDTO;
import com.akash.food.restaurant.service.RestaurantService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurant API", description = "APIs for managing restaurant data")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Create a new restaurant")
    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(
            @Valid @RequestBody RestaurantDTO restaurantDTO) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurantDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing restaurant by ID")
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable String id,
            @Valid @RequestBody RestaurantDTO restaurantDTO) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDTO));
    }

    @Operation(summary = "Get a restaurant by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @Operation(summary = "Search restaurants by name")
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(restaurantService.findRestaurantByName(name));
    }

    @Operation(summary = "Get all restaurants with rate limiting")
    @RateLimiter(name = "get-all-restaurant-rate-limiter", fallbackMethod = "getAllRestaurantsFallback")
    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @Operation(summary = "Delete a restaurant by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurantById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Fallback method for getAllRestaurants rate limiter
     */
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsFallback(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(List.of());
    }
}
