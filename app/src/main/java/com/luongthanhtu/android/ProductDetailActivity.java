package com.luongthanhtu.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luongthanhtu.android.model.ProductItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView tvName, tvPrice, tvDescription;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.imgProduct);
        tvName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvProductPrice);
        tvDescription = findViewById(R.id.tvProductDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        String name = getIntent().getStringExtra("name");
        int image = getIntent().getIntExtra("image", R.drawable.ic_launcher_background);
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");

        tvName.setText(name);
        tvPrice.setText(price);
        tvDescription.setText(description);
        imgProduct.setImageResource(image);

        btnAddToCart.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
            Gson gson = new Gson();

            String jsonCart = sharedPreferences.getString("cartItems", null);
            Type type = new TypeToken<List<ProductItem>>() {}.getType();
            List<ProductItem> cartList = jsonCart != null ? gson.fromJson(jsonCart, type) : new ArrayList<>();

            boolean found = false;
            for (ProductItem item : cartList) {
                if (item.name.equals(name)) {
                    item.quantity += 1;
                    found = true;
                    break;
                }
            }

            if (!found) {
                cartList.add(new ProductItem(name, price, image, 1));
            }

            sharedPreferences.edit().putString("cartItems", gson.toJson(cartList)).apply();
            Toast.makeText(ProductDetailActivity.this, name + " đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });
    }
}
