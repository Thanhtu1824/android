package com.luongthanhtu.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luongthanhtu.android.model.ProductItem;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private final List<ProductItem> items;

    public PaymentAdapter(List<ProductItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        ProductItem item = items.get(position);
        holder.tvName.setText(item.name);
        holder.tvPrice.setText(item.price);
        holder.tvQuantity.setText("Số lượng: " + item.quantity);

        // Load ảnh từ URL thay vì resource
        Glide.with(holder.img.getContext())
                .load(item.imageUrl)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice, tvQuantity;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCartProduct);
            tvName = itemView.findViewById(R.id.tvCartProductName);
            tvPrice = itemView.findViewById(R.id.tvCartProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
        }
    }
}
