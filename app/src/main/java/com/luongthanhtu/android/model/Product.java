package com.luongthanhtu.android.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int imageResId;
    private int price; // giá nguyên: ví dụ 5000000

    public Product(String name, int imageResId, int price) {
        this.name = name;
        this.imageResId = imageResId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getPrice() {
        return price;
    }
}
