package com.luongthanhtu.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luongthanhtu.android.model.ProductItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerViewCart;
    Button btnCheckout;
    CartAdapter cartAdapter;
    List<ProductItem> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Lấy dữ liệu giỏ hàng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonCart = sharedPreferences.getString("cartItems", null);
        Type type = new TypeToken<List<ProductItem>>() {}.getType();
        cartList = jsonCart != null ? gson.fromJson(jsonCart, type) : new ArrayList<>();

        // ✅ Debug log để kiểm tra imageUrl
        Log.d("CartActivity", "Cart JSON: " + jsonCart);
        for (ProductItem p : cartList) {
            Log.d("CartActivity", "Item: " + p.name + " | Image: " + p.imageUrl);
            // Nếu thiếu imageUrl thì gán tạm link test
            if (p.imageUrl == null || p.imageUrl.isEmpty()) {
                p.imageUrl = "https://i.imgur.com/tGbaZCY.jpg";
            }
        }

        // Khởi tạo RecyclerView và adapter
        cartAdapter = new CartAdapter(this, cartList);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(cartAdapter);

        // Nút thanh toán
        btnCheckout.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(CartActivity.this, PaymentActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại danh sách khi trở về Activity
        SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonCart = sharedPreferences.getString("cartItems", null);
        Type type = new TypeToken<List<ProductItem>>() {}.getType();
        cartList.clear();
        if (jsonCart != null) {
            cartList.addAll(gson.fromJson(jsonCart, type));
        }

        // ✅ Debug lại sau khi resume
        for (ProductItem p : cartList) {
            Log.d("CartActivity", "Resume item: " + p.name + " | Image: " + p.imageUrl);
            if (p.imageUrl == null || p.imageUrl.isEmpty()) {
                p.imageUrl = "https://i.imgur.com/tGbaZCY.jpg";
            }
        }

        cartAdapter.notifyDataSetChanged();
    }
}
