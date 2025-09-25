package com.akash.food.restaurant.service.impl;

import com.akash.food.restaurant.dto.RestaurantDTO;
import com.akash.food.restaurant.entities.Restaurant;
import com.akash.food.restaurant.exception.ResourceNotFoundException;
import com.akash.food.restaurant.mapper.RestaurantMapper;
import com.akash.food.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant;
    private RestaurantDTO restaurantDTO;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setId("1");
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setPhone("1234567890");
        restaurant.setOpen(true);
        restaurant.setOpenTime(LocalTime.of(9, 0));
        restaurant.setCloseTime(LocalTime.of(22, 0));
        restaurant.setAboutRestaurant("Test about");
        restaurant.getPictures().add("http://test.com/pic1.jpg");

        restaurantDTO = new RestaurantDTO(
                "1",
                "Test Restaurant",
                "123 Test St",
                "1234567890",
                List.of("http://test.com/pic1.jpg"),
                true,
                LocalTime.of(9, 0),
                LocalTime.of(22, 0),
                "Test about"
        );
    }

    @Test
    public void testCreateRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantDTO result = restaurantService.createRestaurant(restaurantDTO);

        assertNotNull(result);
        assertEquals("Test Restaurant", result.name());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testUpdateRestaurant_Success() {
        when(restaurantRepository.findById("1")).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        RestaurantDTO result = restaurantService.updateRestaurant("1", restaurantDTO);

        assertNotNull(result);
        assertEquals("Test Restaurant", result.name());
        verify(restaurantRepository, times(1)).findById("1");
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testUpdateRestaurant_NotFound() {
        when(restaurantRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.updateRestaurant("1", restaurantDTO);
        });
    }

    @Test
    public void testGetRestaurantById_Success() {
        when(restaurantRepository.findById("1")).thenReturn(Optional.of(restaurant));

        RestaurantDTO result = restaurantService.getRestaurantById("1");

        assertNotNull(result);
        assertEquals("Test Restaurant", result.name());
        verify(restaurantRepository, times(1)).findById("1");
    }

    @Test
    public void testGetRestaurantById_NotFound() {
        when(restaurantRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.getRestaurantById("1");
        });
    }

    @Test
    public void testFindRestaurantByName() {
        when(restaurantRepository.findByNameContainingIgnoreCase("Test"))
                .thenReturn(List.of(restaurant));

        List<RestaurantDTO> result = restaurantService.findRestaurantByName("Test");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(restaurantRepository, times(1)).findByNameContainingIgnoreCase("Test");
    }

    @Test
    public void testGetAllRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));

        List<RestaurantDTO> result = restaurantService.getAllRestaurants();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteRestaurantById_Success() {
        when(restaurantRepository.existsById("1")).thenReturn(true);
        doNothing().when(restaurantRepository).deleteById("1");

        restaurantService.deleteRestaurantById("1");

        verify(restaurantRepository, times(1)).existsById("1");
        verify(restaurantRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteRestaurantById_NotFound() {
        when(restaurantRepository.existsById("1")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            restaurantService.deleteRestaurantById("1");
        });
    }
}
