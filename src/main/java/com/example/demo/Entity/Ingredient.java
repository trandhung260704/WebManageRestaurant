package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "\"ingredient\"")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idingredient;
    private int priceingredient;
    private int quantity;
    private String origin;
    private String unit;
    private String name;

}