package com.VDVS.food.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.VDVS.food.model.Cart;
import com.VDVS.food.model.Food;
import com.VDVS.food.model.NewCart;
import com.VDVS.food.model.NewFood;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CartDaoImpl {
    @Autowired
    private CartDao cartDao;

    @Autowired
    private FoodDao foodDao;

    @Value("${fileStorage}")
    private String storagePath;

    @Transactional
    public void saveToCart(NewCart[] newCarts) {
        boolean hasValidQuantity = false;
        for (NewCart item : newCarts) {
            if (item.getQuantity() > 0) {
                hasValidQuantity = true;
                break;
            }
        }

        if (!hasValidQuantity) {
            throw new IllegalArgumentException("Please select at least one item.");
        }

        cartDao.deleteAll();
        cartDao.flush();

        Cart cart = new Cart(
                newCarts.length > 0 ? newCarts[0].getQuantity() : 0,
                newCarts.length > 1 ? newCarts[1].getQuantity() : 0,
                newCarts.length > 2 ? newCarts[2].getQuantity() : 0,
                newCarts.length > 3 ? newCarts[3].getQuantity() : 0,
                newCarts.length > 4 ? newCarts[4].getQuantity() : 0,
                newCarts.length > 5 ? newCarts[5].getQuantity() : 0
        );

        cartDao.save(cart);
    }

    @Transactional
    public void updateDB() {
        List<Cart> carts = cartDao.findAll();
        if (carts.isEmpty()) return;

        Cart cart = carts.get(0);

        List<Food> foods = foodDao.findAll();

        if (foods.size() > 0) foods.get(0).setQuantity(foods.get(0).getQuantity() - cart.getQuantity1());
        if (foods.size() > 1) foods.get(1).setQuantity(foods.get(1).getQuantity() - cart.getQuantity2());
        if (foods.size() > 2) foods.get(2).setQuantity(foods.get(2).getQuantity() - cart.getQuantity3());
        if (foods.size() > 3) foods.get(3).setQuantity(foods.get(3).getQuantity() - cart.getQuantity4());
        if (foods.size() > 4) foods.get(4).setQuantity(foods.get(4).getQuantity() - cart.getQuantity5());
        if (foods.size() > 5) foods.get(5).setQuantity(foods.get(5).getQuantity() - cart.getQuantity6());

        foodDao.saveAll(foods);

        // Reset cart
        cart.setQuantity1(0);
        cart.setQuantity2(0);
        cart.setQuantity3(0);
        cart.setQuantity4(0);
        cart.setQuantity5(0);
        cart.setQuantity6(0);

        cartDao.save(cart);
    }

    public List<Cart> getAllCart() {
        return cartDao.findAll();
    }

    @Transactional
    public void addItems(NewCart[] newCarts) {
        List<Food> foods = foodDao.findAll();
        for (int i = 0; i < foods.size() && i < newCarts.length; i++) {
            foods.get(i).setQuantity(foods.get(i).getQuantity() + newCarts[i].getQuantity());
        }
        foodDao.saveAll(foods);
    }

    public boolean addNewItem(MultipartFile file, String newFoodData) throws IOException {
        NewFood newFood = new ObjectMapper().readValue(newFoodData, NewFood.class);

        if (!file.isEmpty() && saveFileToAssets(file)) {
            foodDao.save(new Food(
                    newFood.getId(),
                    newFood.getName(),
                    newFood.getPrice(),
                    newFood.getQuantityAvailable(),
                    "/assets/" + file.getOriginalFilename(),
                    "", ""
            ));
        }
        return true;
    }

    public boolean addNewItemWithUrl(String newFoodData) throws IOException {
        NewFood newFood = new ObjectMapper().readValue(newFoodData, NewFood.class);

        foodDao.save(new Food(
                newFood.getId(),
                newFood.getName(),
                newFood.getPrice(),
                newFood.getQuantityAvailable(),
                newFood.getFileDataF(),
                "", ""
        ));
        return true;
    }

    private boolean saveFileToAssets(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(storagePath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filepath = uploadPath.resolve(file.getOriginalFilename());
        file.transferTo(filepath.toFile());

        return true;
    }

    public int calculateTotal(NewCart[] newCarts) {
        int total = 0;
        List<Food> foods = foodDao.findAll();

        for (int i = 0; i < foods.size() && i < newCarts.length; i++) {
            total += foods.get(i).getPrice() * newCarts[i].getQuantity();
        }
        return total;
    }

    public boolean itemIdAvailable(String itemId) {
        return foodDao.findById(itemId).isPresent();
    }
}
