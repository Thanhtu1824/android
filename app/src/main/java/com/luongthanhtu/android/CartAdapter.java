package com.luongthanhtu.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luongthanhtu.android.model.ProductItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<ProductItem> cartList;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public CartAdapter(Context context, List<ProductItem> cartList) {
        this.context = context;
        this.cartList = cartList;
        sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductItem item = cartList.get(position);

        holder.tvName.setText(item.name);
        holder.tvPrice.setText(item.price);
        holder.tvQuantity.setText(String.valueOf(item.quantity));

        // ✅ Load ảnh từ URL bằng Glide
        Glide.with(context)
                .load(item.imageUrl) // link ảnh từ ProductItem
                .into(holder.img);

        // Tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            item.quantity++;
            holder.tvQuantity.setText(String.valueOf(item.quantity));
            saveCart();
        });

        // Giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (item.quantity > 1) {
                item.quantity--;
                holder.tvQuantity.setText(String.valueOf(item.quantity));
                saveCart();
            }
        });

        // Xóa sản phẩm
        holder.btnRemove.setOnClickListener(v -> {
            cartList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartList.size());
            saveCart();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private void saveCart() {
        sharedPreferences.edit().putString("cartItems", gson.toJson(cartList)).apply();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice, tvQuantity;
        Button btnIncrease, btnDecrease, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCartProduct);
            tvName = itemView.findViewById(R.id.tvCartProductName);
            tvPrice = itemView.findViewById(R.id.tvCartProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
