package com.luongthanhtu.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // ❗ Đảm bảo trong activity_login.xml có LinearLayout hoặc FrameLayout với id là "main"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Gán nút Đăng nhập
        Button btnLogin = findViewById(R.id.btnLogin);

        // Gán nút Đăng ký nếu có
        Button btnRegister = findViewById(R.id.btnRegister);  // ❗ Nếu chưa có thì tạo trong XML

        // Bắt sự kiện đăng nhập
        btnLogin.setOnClickListener(v -> {
            Intent it = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(it);
        });

        // Bắt sự kiện đăng ký
        btnRegister.setOnClickListener(v -> {
            Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(it);
        });
    }
}
