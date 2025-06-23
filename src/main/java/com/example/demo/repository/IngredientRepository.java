package com.example.demo.repository;

import com.example.demo.Entity.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{
    Page<Ingredient> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
