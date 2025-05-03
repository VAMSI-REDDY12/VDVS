package com.VDVS.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.VDVS.food.model.Contact;

public interface ContactDao extends JpaRepository<Contact,Integer> {
}
