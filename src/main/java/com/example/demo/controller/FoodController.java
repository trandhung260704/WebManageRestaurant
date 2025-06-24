
package com.example.demo.controller;

import com.example.demo.Entity.*;
import com.example.demo.repository.*;
import com.example.demo.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class FoodController {
    private final FoodRepository foodRepo;
    private final FoodIngredientRepository foodIngredientRepo;

    public FoodController(FoodRepository foodRepo, FoodIngredientRepository foodIngredientRepo) {
        this.foodRepo = foodRepo;
        this.foodIngredientRepo = foodIngredientRepo;
    }

    // FOOD API
    // === FOOD API ===

    @GetMapping("/foods")
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.ok(foodRepo.findAll());
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable int id) {
        return foodRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/foods")
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        return ResponseEntity.ok(foodRepo.save(food));
    }

    @PutMapping("/foods/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable int id, @RequestBody Food updatedFood) {
        return foodRepo.findById(id)
                .map(food -> {
                    food.setNamefood(updatedFood.getNamefood());
                    food.setFoodimage(updatedFood.getFoodimage());
                    return ResponseEntity.ok(foodRepo.save(food));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/foods/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable int id) {
        if (foodRepo.existsById(id)) {
            foodRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/foods/with-ingredients")
    public ResponseEntity<String> createFoodWithIngredients(@RequestBody FoodWithIngredientsRequest request) {
        Food savedFood = foodRepo.save(request.getFood());

        if (request.getIngredients() != null) {
            for (IngredientQuantity iq : request.getIngredients()) {
                Food_Ingredient fi = new Food_Ingredient(savedFood.getIdfood(), iq.getIdingredient(), iq.getQuantityingredient());
                foodIngredientRepo.save(fi);
            }
        }

        return ResponseEntity.ok("Food and ingredients saved");
    }

    @GetMapping("/foods/page")
    public ResponseEntity<Page<Food>> getFoodsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Food> foodPage;

        if (keyword == null || keyword.trim().isEmpty()) {
            foodPage = foodRepo.findAll(pageable);
        } else {
            foodPage = foodRepo.findByNamefood(keyword.trim(), pageable);
        }

        return ResponseEntity.ok(foodPage);
    }

}
