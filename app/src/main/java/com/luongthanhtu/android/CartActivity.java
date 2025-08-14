package com.luongthanhtu.android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerViewCart;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        // Giả lập dữ liệu giỏ hàng
        List<String> cartItems = Arrays.asList(
                "CPU Intel Core i5",
                "RAM DDR4 16GB",
                "Card đồ họa RTX 3060"
        );

        // Adapter tạm (bạn có thể tạo adapter riêng cho giỏ hàng)
        SimpleCartAdapter adapter = new SimpleCartAdapter(cartItems);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(adapter);

        // Sự kiện thanh toán
        btnCheckout.setOnClickListener(v ->
                Toast.makeText(CartActivity.this, "Chuyển sang trang thanh toán", Toast.LENGTH_SHORT).show()
        );
    }
}
