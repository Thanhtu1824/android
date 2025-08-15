package com.luongthanhtu.android;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luongthanhtu.android.model.ProductItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    RecyclerView recyclerViewPayment;
    PaymentAdapter paymentAdapter;
    RadioGroup radioGroupPayment;
    RadioButton rbFull, rbInstallment;
    TextView tvTotal, tvMonthly;
    Spinner spinnerMonths;
    Button btnPay;

    List<ProductItem> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detailed);

        recyclerViewPayment = findViewById(R.id.recyclerViewPayment);
        radioGroupPayment = findViewById(R.id.radioGroupPayment);
        rbFull = findViewById(R.id.rbFullPayment);
        rbInstallment = findViewById(R.id.rbInstallment);
        tvTotal = findViewById(R.id.tvTotal);
        tvMonthly = findViewById(R.id.tvMonthly);
        spinnerMonths = findViewById(R.id.spinnerMonths);
        btnPay = findViewById(R.id.btnPay);

        loadCartItems();
        setupRecyclerView();

        double total = calculateTotal();
        tvTotal.setText("Tổng tiền: " + String.format("%,.0f", total) + " đ");

        // Spinner số tháng trả góp
        Integer[] months = {3, 6, 9, 12};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapter);
        spinnerMonths.setEnabled(false);

        rbFull.setOnClickListener(v -> tvMonthly.setText(""));
        rbInstallment.setOnClickListener(v -> updateMonthly());

        spinnerMonths.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                updateMonthly();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        btnPay.setOnClickListener(v -> {
            int selectedId = radioGroupPayment.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(PaymentActivity.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
            } else if (selectedId == rbFull.getId()) {
                Toast.makeText(PaymentActivity.this, "Thanh toán toàn bộ: " + String.format("%,.0f", total) + " đ", Toast.LENGTH_LONG).show();
            } else if (selectedId == rbInstallment.getId()) {
                int monthsSelected = (Integer) spinnerMonths.getSelectedItem();
                double monthly = total / monthsSelected;
                Toast.makeText(PaymentActivity.this, "Trả góp " + monthsSelected + " tháng, mỗi tháng: " + String.format("%,.0f", monthly) + " đ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadCartItems() {
        cartList = new ArrayList<>();
        Gson gson = new Gson();
        String jsonCart = getSharedPreferences("CartPrefs", MODE_PRIVATE).getString("cartItems", null);
        if (jsonCart != null) {
            Type type = new TypeToken<List<ProductItem>>() {}.getType();
            cartList = gson.fromJson(jsonCart, type);
        }
    }

    private void setupRecyclerView() {
        paymentAdapter = new PaymentAdapter(cartList);
        recyclerViewPayment.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPayment.setAdapter(paymentAdapter);
    }

    private double calculateTotal() {
        double total = 0;
        for (ProductItem item : cartList) {
            try {
                String priceStr = item.price.replaceAll("[^\\d]", "");
                double price = Double.parseDouble(priceStr);
                total += price * item.quantity;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    private void updateMonthly() {
        spinnerMonths.setEnabled(rbInstallment.isChecked());
        int monthsSelected = (Integer) spinnerMonths.getSelectedItem();
        double total = calculateTotal();
        double monthly = total / monthsSelected;
        tvMonthly.setText("Mỗi tháng: " + String.format("%,.0f", monthly) + " đ");
    }
}
