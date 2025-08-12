package com.luongthanhtu.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.luongthanhtu.android.api.ApiService;
import com.luongthanhtu.android.api.RetrofitClient;
import com.luongthanhtu.android.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText  txtEmail, txtNumphone,txtUsername, txtPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        txtEmail = findViewById(R.id.txtEmail);
        txtNumphone = findViewById(R.id.txtNumphone);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {

            String email = txtEmail.getText().toString().trim();
            String numphone = txtNumphone.getText().toString().trim();
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || numphone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra định dạng email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isStrongPassword(password)) {
                Toast.makeText(this, "Mật khẩu phải ≥ 8 ký tự, có chữ hoa, chữ thường, số và ký tự đặc biệt!", Toast.LENGTH_LONG).show();
                return;
            }

            User newUser = new User(email, numphone, username, password);

            ApiService apiService = RetrofitClient.getClient("https://68931c15c49d24bce869790e.mockapi.io/")
                    .create(ApiService.class);

            apiService.createUser(newUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Lỗi đăng ký: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Kết nối API thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    // Hàm kiểm tra mật khẩu mạnh
    private boolean isStrongPassword (String password){
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }
}
