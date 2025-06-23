package com.example.demo.Entity;

import com.example.demo.dto.FoodIngredientId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@Entity
@IdClass(FoodIngredientId.class)
@Table(name = "food_ingredient")
public class Food_Ingredient implements Serializable {

    @Id
    private int idfood;

    @Id
    private int idingredient;

    private int quantityingredient;

    public Food_Ingredient() {}

    public Food_Ingredient(int idfood, int idingredient, int quantityingredient) {
        this.idfood = idfood;
        this.idingredient = idingredient;
        this.quantityingredient = quantityingredient;
    }
}
