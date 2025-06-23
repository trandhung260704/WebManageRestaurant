package com.example.demo.dto;

import java.io.Serializable;
import java.util.Objects;

public class FoodIngredientId implements Serializable {
    private int idfood;
    private int idingredient;

    public FoodIngredientId() {}

    public FoodIngredientId(int idfood, int idingredient) {
        this.idfood = idfood;
        this.idingredient = idingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodIngredientId)) return false;
        FoodIngredientId that = (FoodIngredientId) o;
        return idfood == that.idfood && idingredient == that.idingredient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idfood, idingredient);
    }
}
