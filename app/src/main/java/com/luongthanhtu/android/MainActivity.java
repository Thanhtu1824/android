package com.luongthanhtu.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnBack;
    TextView txtHome;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHome = findViewById(R.id.txtHome);
        btnBack = findViewById(R.id.btnBack);

        String username = getIntent().getStringExtra("username");
        if (username!=null)
        {
            txtHome.setText("Xin chào "+ username);
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }



}
