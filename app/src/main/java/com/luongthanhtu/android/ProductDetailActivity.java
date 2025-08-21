package com.luongthanhtu.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luongthanhtu.android.api.RetrofitClient;
import com.luongthanhtu.android.api.ApiService;
import com.luongthanhtu.android.model.ProductItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView tvName, tvPrice, tvDescription;
    Button btnAddToCart;
    RecyclerView rvOtherProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        imgProduct = findViewById(R.id.imgProduct);
        tvName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvProductPrice);
        tvDescription = findViewById(R.id.tvProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        rvOtherProducts = findViewById(R.id.rvOtherProducts);

        // Nhận dữ liệu sản phẩm từ Intent
        String name = getIntent().getStringExtra("name");
        String imageUrl = getIntent().getStringExtra("imageUrl"); // đổi sang URL thay vì resource
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");

        // Gán dữ liệu lên UI
        tvName.setText(name);
        tvPrice.setText(price);
        tvDescription.setText(description);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgProduct);

        // 🛒 Xử lý thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
            Gson gson = new Gson();

            String jsonCart = sharedPreferences.getString("cartItems", null);
            Type type = new TypeToken<List<ProductItem>>() {}.getType();
            List<ProductItem> cartList = jsonCart != null ? gson.fromJson(jsonCart, type) : new ArrayList<>();

            boolean found = false;
            for (ProductItem item : cartList) {
                if (item.name.equals(name)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                cartList.add(new ProductItem(null, name, price, description, imageUrl, 1)); // mặc định số lượng = 1
            }

            sharedPreferences.edit().putString("cartItems", gson.toJson(cartList)).apply();
            Toast.makeText(ProductDetailActivity.this, name + " đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // 🆕 Load sản phẩm khác từ MockAPI
        loadOtherProducts();
    }

    private void loadOtherProducts() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        apiService.getProducts().enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductItem>> call, @NonNull Response<List<ProductItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductItem> productList = response.body();

                    LinearLayoutManager layoutManager =
                            new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rvOtherProducts.setLayoutManager(layoutManager);

                    ProductAdapter adapter = new ProductAdapter(ProductDetailActivity.this, productList);
                    rvOtherProducts.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductItem>> call, @NonNull Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Lỗi tải sản phẩm khác", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
