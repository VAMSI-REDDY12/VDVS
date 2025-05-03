package com.VDVS.food.controller;

import java.io.IOException;
import java.util.List;   // <-- IMPORTANT for List

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VDVS.food.dao.FoodDao;
import com.VDVS.food.model.Food;
import com.VDVS.food.model.NewFood;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodDao foodDao;

    @PostMapping("/addWithUrl")
    public boolean addNewItemWithUrl(@RequestBody String newFoodData) throws IOException {
        // Parsing JSON into NewFood object
        NewFood newFood = new ObjectMapper().readValue(newFoodData, NewFood.class);

        // Create Food entity and map fields properly
        Food food = new Food(
            newFood.getId(),                 
            newFood.getName(),                
            newFood.getPrice(),               
            newFood.getQuantityAvailable(),   
            newFood.getFileDataF(),            
            "",                               
            ""                               
        );

        foodDao.save(food);
        return true;
    }

    @GetMapping("/all")
    public List<Food> getAllFoodItems() {
        return foodDao.findAll();
    }
}
