package com.luongthanhtu.android.model;

import com.google.gson.annotations.SerializedName;

public class ProductItem {
    public String id;

    @SerializedName("productName")
    public String name;

    public String price;

    @SerializedName("image")
    public String imageUrl;

    public String description;
    public int quantity;

    // Constructor rỗng (bắt buộc cho Gson/Retrofit)
    public ProductItem() {}

    // Constructor cho API
    public ProductItem(String id, String name, String price, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.quantity = 1;
    }

    // Constructor cho giỏ hàng
    public ProductItem(String id, String name, String price, String imageUrl, String description, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.quantity = quantity;
    }
}
