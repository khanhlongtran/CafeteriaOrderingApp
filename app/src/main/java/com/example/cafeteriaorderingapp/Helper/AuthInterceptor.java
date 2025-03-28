package com.example.cafeteriaorderingapp.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Lấy SharedPreferences toàn cục
        Context context = MyApplication.getAppContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("JWT_TOKEN", null);
        Log.d("AuthInterceptor", "Token lấy được: " + token);
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }

        Request newRequest = builder.build();
        Log.d("AuthInterceptor", "Headers gửi đi: " + newRequest.headers());
        return chain.proceed(newRequest);
    }
}
