package com.VDVS.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VDVS.food.model.Cart;

public interface CartDao extends JpaRepository<Cart,Integer> {
}
