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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText txtNewPassword, txtConfirmPassword;
    TextView tvGoLogin;
    private Button btnChangePassword;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        tvGoLogin = findViewById(R.id.tvGoLogin);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        userId = getIntent().getStringExtra("USER_ID");

        tvGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnChangePassword.setOnClickListener(v -> {
            String newPass = txtNewPassword.getText().toString().trim();
            String confirmPass = txtConfirmPassword.getText().toString().trim();

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            User updateUser = new User();
            updateUser.setPassword(newPass);

            apiService.updateUser(userId, updateUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn hình trước
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Lỗi khi đổi mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ResetPasswordActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                }
            });

        });

    }
}
