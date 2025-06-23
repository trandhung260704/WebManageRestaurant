package com.example.demo.controller;

import com.example.demo.repository.FoodRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class SearchController {
    private final FoodRepository foodRepo;

    public SearchController(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

}

