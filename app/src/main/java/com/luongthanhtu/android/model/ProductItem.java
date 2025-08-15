package com.luongthanhtu.android.model;

import java.io.Serializable;

public class ProductItem implements Serializable {
    public String name;
    public String price;
    public int image;
    public int quantity;

    public ProductItem(String name, String price, int image, int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
}
