package com.model;

public class Resource {
    
    private String name;
    private int quantity;
    private String unit;

    public Resource(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

}
