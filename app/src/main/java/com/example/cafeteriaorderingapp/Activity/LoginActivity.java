package com.example.cafeteriaorderingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cafeteriaorderingapp.Activity.Deliverer.HomeDelivererActivity;
import com.example.cafeteriaorderingapp.Dto.LoginRequest;
import com.example.cafeteriaorderingapp.Dto.LoginResponse;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String savedToken = sharedPreferences.getString("JWT_TOKEN", null);

        if (savedToken != null) {
            String savedRole = sharedPreferences.getString("USER_ROLE", null);
            if (savedRole != null) {
                navigateTo(savedRole); // Chuyển hướng dựa trên role
            } else {
                Log.e("TAG", "Không tìm thấy USER_ROLE trong SharedPreferences");
            }
        }

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                login(email, password);
            } else {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String email, String password) {
        ApiService apiService = RetrofitClient.getDetailService();
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Log.d("LoginActivity", "onResponse: " + loginResponse.getRole());
                    // Lưu thông tin đăng nhập vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("JWT_TOKEN", loginResponse.getToken());
                    editor.putString("USER_ROLE", loginResponse.getRole());
                    editor.putString("ACCOUNT_ID", loginResponse.getAccountId());
                    editor.apply();

                    Log.d("TAG", "Login thành công, chuyển intent");
                    navigateTo(loginResponse.getRole());
                } else {
                    Log.e("TAG", "Login thất bại! Mã lỗi HTTP: " + response.code());
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("TAG", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Lỗi kết nối, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void navigateTo(String role) {
        Intent intent;
        if ("PATRON".equalsIgnoreCase(role)) {
            intent = new Intent(LoginActivity.this, MainActivity.class);
        } else if ("DELIVER".equalsIgnoreCase(role)) {
            intent = new Intent(LoginActivity.this, HomeDelivererActivity.class);
        } else {
            Log.e("TAG", "Role không hợp lệ: " + role);
            Toast.makeText(LoginActivity.this, "Vai trò không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(intent);
        finish(); // Đóng màn hình login sau khi chuyển hướng
    }

}