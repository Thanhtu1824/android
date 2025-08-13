package com.luongthanhtu.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLogout;
    TextView tvGreeting;
    ListView listView;

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view
        tvGreeting = findViewById(R.id.tvGreeting);
        btnLogout = findViewById(R.id.btnLogout);
        listView = findViewById(R.id.listView); // <- QUAN TRỌNG

        // Lấy username từ Intent
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            tvGreeting.setText("Xin chào " + username);
        }

        // Dữ liệu ListView
        String[] items = {"Sản phẩm 1", "Sản phẩm 2", "Sản phẩm 3", "Sản phẩm 4"};
        int[] icons = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

        // Adapter tùy chỉnh
        CustomAdapter customAdapter = new CustomAdapter(this, items, icons);
        listView.setAdapter(customAdapter);

        // Bắt sự kiện click ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this,
                    "Bạn chọn: " + items[position], Toast.LENGTH_SHORT).show();
        });

        // Nút Logout
        btnLogout.setOnClickListener(v -> {
            // Quay về LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Xóa MainActivity khỏi stack
        });
    }
}
