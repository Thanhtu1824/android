package com.luongthanhtu.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.luongthanhtu.android.model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ProductAdapter productAdapter;
    CategoryAdapter categoryAdapter;
    String username;
    ImageView bannerImage; // Banner 1 ảnh
    EditText edtSearch;    // Ô tìm kiếm mới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Lấy username từ Intent
        username = getIntent().getStringExtra("username");

        // Header NavigationView
        View headerView = navigationView.getHeaderView(0);
        TextView headerUsername = headerView.findViewById(R.id.headerUsername);
        TextView tvGreeting = headerView.findViewById(R.id.tvGreeting);
        if (username != null) {
            headerUsername.setText(username);
            tvGreeting.setText("Xin chào " + username);
        }

        // Banner ảnh cố định
        bannerImage = findViewById(R.id.bannerImage);
        bannerImage.setImageResource(R.drawable.ic_banner);

        // RecyclerView sản phẩm
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        String[] items = {
                "CPU Intel Core i5",
                "RAM DDR4 16GB",
                "Card đồ họa RTX 3060",
                "Mainboard ASUS B450",
                "SSD Samsung 1TB",
                "Nguồn Corsair 650W",
                "Vỏ Case CoolerMaster"
        };

        int[] icons = {
                R.drawable.corei5,
                R.drawable.ram16,
                R.drawable.c3060,
                R.drawable.b450,
                R.drawable.ssd1tb,
                R.drawable.ps650w,
                R.drawable.casepc
        };

        productAdapter = new ProductAdapter(this, Arrays.asList(items), icons);
        recyclerView.setAdapter(productAdapter);

        // Ô tìm kiếm mới
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Nút giỏ hàng
        ImageButton btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        // RecyclerView Category
        RecyclerView rvCategory = findViewById(R.id.recyclerViewCategory);
        rvCategory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("CPU", R.drawable.ic_cpu));
        categories.add(new Category("RAM", R.drawable.ic_ram));
        categories.add(new Category("GPU", R.drawable.ic_gpu));
        categories.add(new Category("Mainboard", R.drawable.ic_mainboard));
        categories.add(new Category("SSD", R.drawable.ic_ssd_drive));
        categories.add(new Category("PSU", R.drawable.ic_psu));
        categories.add(new Category("Case", R.drawable.ic_computer_case));

        categoryAdapter = new CategoryAdapter(this, categories);
        rvCategory.setAdapter(categoryAdapter);

        // Navigation Drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_user_info) {
                Toast.makeText(MainActivity.this, "User: " + username, Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.nav_logout) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void filterProducts(String text) {
        List<String> items = Arrays.asList(
                "CPU Intel Core i5",
                "RAM DDR4 16GB",
                "Card đồ họa RTX 3060",
                "Mainboard ASUS B450",
                "SSD Samsung 1TB",
                "Nguồn Corsair 650W",
                "Vỏ Case CoolerMaster"
        );

        List<String> filteredList = new ArrayList<>();
        for (String product : items) {
            if (product.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.updateData(filteredList);
    }

    // Ẩn bàn phím và mất focus khi click ra ngoài EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    // Ẩn bàn phím
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    // Mất focus
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
