package com.luongthanhtu.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.luongthanhtu.android.api.ApiService;
import com.luongthanhtu.android.api.RetrofitClient;
import com.luongthanhtu.android.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView tvGoLogin;
    private EditText txtEmail;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvGoLogin = findViewById(R.id.tvGoLogin);
        txtEmail = findViewById(R.id.txtEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            apiService.getUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        boolean found = false;
                        String foundUserId = null;

                        for (User user : response.body()) {
                            if (email.equalsIgnoreCase(user.getEmail())) {
                                found = true;
                                foundUserId = user.getId();
                                break;
                            }
                        }

                        if (found) {
                            // Chuyển sang màn hình ResetPassword và truyền id user
                            Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("USER_ID", foundUserId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Email không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(ForgotPasswordActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                }
            });
        });
        tvGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
