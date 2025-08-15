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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        String productName = productList.get(position);
        int productImage = productIcons[position % productIcons.length];

        holder.textView.setText(productName);
        holder.imageView.setImageResource(productImage);

        // 🆕 Click vào sản phẩm -> mở ProductDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", productName);
            intent.putExtra("image", productImage);

            // Tạo giá tự động theo ví dụ
            int price = (position + 1) * 1000000;
            String priceStr = NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(price);
            intent.putExtra("price", "Giá: " + priceStr + " đ");

            // Tạo mô tả tự động theo tên sản phẩm
            intent.putExtra("description", "Mô tả chi tiết sản phẩm " + productName);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Cập nhật danh sách sản phẩm (dùng cho tìm kiếm)
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
