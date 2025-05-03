package com.VDVS.food.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.VDVS.food.model.Food;

@Service
public class FoodDaoImpl {
    @Autowired
    private FoodDao foodDao;

    public List<Food> getFoodList() {
        return foodDao.findAll();
    }

    public Food validateFoodInfo(String productId) {
        return foodDao.findById(productId).orElse(null);
    }

    public Food saveFood(Food food) {
        return foodDao.save(food);
    }
}
