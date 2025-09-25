package com.akash.food.restaurant.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String address;
    private String phone;
    //@ElementCollection is used to map a collection of simple values like Strings or Integers for an entity. Hibernate creates a separate table to store these values with a foreign key to the parent entity. It’s useful when we don’t need a separate entity class
    @ElementCollection
    @CollectionTable(
            name = "restaurant_pictures",
            joinColumns = @JoinColumn(name = "restaurant_id")
    )
    @Column(name = "picture_url")
    private List<String> pictures = new ArrayList<>();

    // priority 1
    // if restaurant open  then true if close  false
    private boolean open = false;

    // priority 2
    private LocalTime openTime;
    private LocalTime closeTime;

    @Lob
    private String aboutRestaurant;


}