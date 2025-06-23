package com.example.demo.controller;

import com.example.demo.Entity.*;
import com.example.demo.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class IngredientController {
    private final IngredientRepository ingredientRepo;

    public IngredientController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    //INGREDIENT API
    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientRepo.findAll());
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int id) {
        return ingredientRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientRepo.save(ingredient));
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @RequestBody Ingredient updatedIngredient) {
        return ingredientRepo.findById(id)
                .map(ingredient -> {
                    ingredient.setIdingredient(updatedIngredient.getIdingredient());
                    ingredient.setPriceingredient(updatedIngredient.getPriceingredient());
                    ingredient.setQuantity(updatedIngredient.getQuantity());
                    ingredient.setUnit(updatedIngredient.getUnit());
                    ingredient.setOrigin(updatedIngredient.getOrigin());
                    return ResponseEntity.ok(ingredientRepo.save(ingredient));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        if (ingredientRepo.existsById(id)) {
            ingredientRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
