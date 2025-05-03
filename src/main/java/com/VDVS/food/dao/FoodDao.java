package com.VDVS.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VDVS.food.model.Food;

public interface FoodDao extends JpaRepository<Food, String> {
}

