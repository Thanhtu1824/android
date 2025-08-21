package com.luongthanhtu.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luongthanhtu.android.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<ProductItem> productList = new ArrayList<>();

    // Constructor
    public ProductAdapter(Context context, List<ProductItem> productList) {
        this.context = context;
        if (productList != null) {
            this.productList = productList;
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductItem product = productList.get(position);

        // Set text
        holder.textName.setText(product.name != null ? product.name : "No name");
        holder.textPrice.setText(product.price != null ? product.price : "0");

        // Load ảnh từ URL bằng Glide
        Glide.with(context)
                .load(product.imageUrl) // dùng field "image" trong ProductItem (URL từ MockAPI)
                .placeholder(R.drawable.ic_launcher_background) // ảnh tạm
                .error(R.drawable.ic_launcher_background)       // ảnh lỗi
                .into(holder.imageView);

        // Click vào sản phẩm -> mở ProductDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", product.name);
            intent.putExtra("price", product.price);
            intent.putExtra("description", product.description);
            intent.putExtra("imageUrl", product.imageUrl); // truyền URL ảnh
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    // Cập nhật dữ liệu khi load API xong
    public void updateData(List<ProductItem> newList) {
        if (newList != null) {
            this.productList = newList;
            notifyDataSetChanged();
        }
    }

    // ViewHolder
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textName, textPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            textName = itemView.findViewById(R.id.productName);
            textPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
