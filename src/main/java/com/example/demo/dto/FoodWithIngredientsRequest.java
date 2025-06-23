package com.example.demo.dto;

import com.example.demo.Entity.Food;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FoodWithIngredientsRequest {
    private Food food;
    private List<IngredientQuantity> ingredients;

}
