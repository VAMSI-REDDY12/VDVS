package com.VDVS.food.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.VDVS.food.model.Contact;

@Service
public class ContactDaoImpl {

    @Autowired
    private ContactDao contactDao; 
    public boolean saveMessage(Contact contact) {
        contactDao.save(contact); // Save contact to database
        return true;
    }
}
