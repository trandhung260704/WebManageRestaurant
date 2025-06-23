package com.example.demo.repository;

import com.example.demo.Entity.Food_Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodIngredientRepository extends JpaRepository<Food_Ingredient, Integer>{
}
