package com.akash.food.restaurant.dto;

import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public record RestaurantDTO(

        String id,
        @NotBlank(message = "Restaurant name cannot be blank")
        @Size(min = 2, max = 100, message = "Restaurant name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Address cannot be blank")
        @Size(min = 5, max = 250, message = "Address must be between 5 and 250 characters")
        String address,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
        String phone,

        @NotEmpty(message = "At least one picture URL is required")
        List<
                @NotBlank(message = "Picture URL cannot be blank")
                @Pattern(regexp = "^(http|https)://.*$", message = "Picture URL must be valid")
                        String> pictures,

        boolean open,

        @NotNull(message = "Opening time is required")
        LocalTime openTime,

        @NotNull(message = "Closing time is required")
        LocalTime closeTime,

        @Size(max = 2000, message = "About Restaurant must not exceed 2000 characters")
        String aboutRestaurant
) {}
