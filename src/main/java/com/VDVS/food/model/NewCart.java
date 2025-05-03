package com.VDVS.food.model;

public class NewCart {
    private String id;
    private int quantity;

    public NewCart() {}

    public NewCart(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "NewCart{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
