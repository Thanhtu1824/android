package com.luongthanhtu.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<String> productList;
    private int[] productIcons;

    public ProductAdapter(Context context, List<String> productList, int[] productIcons) {
        this.context = context;
        this.productList = productList;
        this.productIcons = productIcons;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.textView.setText(productList.get(position));
        holder.imageView.setImageResource(productIcons[position % productIcons.length]);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // 🆕 Hàm để cập nhật danh sách khi tìm kiếm
    public void updateData(List<String> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            textView = itemView.findViewById(R.id.productName);
        }
    }
}
