package com.VDVS.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VDVS.food.model.User;

public interface UserDao extends JpaRepository<User,String> {
}
