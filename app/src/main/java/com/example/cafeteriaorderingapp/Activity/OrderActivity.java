package com.example.cafeteriaorderingapp.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Adapter.OrderAdapter;
import com.example.cafeteriaorderingapp.Adapter.OrderHistoryAdapter;
import com.example.cafeteriaorderingapp.Dto.Order;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends BaseActivity {
    private RecyclerView orderHistoryRecyclerView, currentOrderRecyclerView;
    private OrderHistoryAdapter orderHistoryAdapter;
    private OrderAdapter orderAdapter;
    private List<Order> orderCompleList = new ArrayList<>();
    private List<Order> orderCurrentList = new ArrayList<>();
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderHistoryRecyclerView = findViewById(R.id.orderHistoryRecyclerView);
        orderHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentOrderRecyclerView= findViewById(R.id.orderCurrentRecyclerView);
        currentOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userIdStr  = sharedPreferences.getString("ACCOUNT_ID", null);
        userId = Integer.parseInt(userIdStr);
        fetchOrderHistory(userId);
        fetchCurrentOrders(userId);
    }

    private void fetchOrderHistory(int userId) {
        ApiService detailService = RetrofitClient.getDetailService();
        Call<List<Order>> call = detailService.getUserOrders(userId);
        Log.d("OrderActivity", "fetchOrderHistory: " + userId);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.d("OrderActivity", "Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Order> allOrders = response.body();
                    Log.d("OrderActivity", "Response Body: " + new Gson().toJson(allOrders));

                    orderCompleList.clear();
                    for (Order order : allOrders) {
                        if ("COMPLETED".equalsIgnoreCase(order.getStatus())) {
                            orderCompleList.add(order);
                        }
                    }

                    if (orderCompleList.isEmpty()) {
                        Toast.makeText(OrderActivity.this, "Không có đơn hàng hoàn thành", Toast.LENGTH_SHORT).show();
                    } else {
                        orderHistoryAdapter = new OrderHistoryAdapter(OrderActivity.this, orderCompleList);
                        orderHistoryRecyclerView.setAdapter(orderHistoryAdapter);
                    }
                } else {
                    Log.e("OrderActivity", "Response Error: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    private void fetchCurrentOrders(int userId) {
        ApiService detailService = RetrofitClient.getDetailService();
        Call<List<Order>> call = detailService.getUserOrders(userId);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.d("OrderActivity", "Current Order Response Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Order> allOrders = response.body();


                    orderCurrentList.clear();
                    for (Order order : allOrders) {
                        if (!"COMPLETED".equalsIgnoreCase(order.getStatus())) {
                            orderCurrentList.add(order);
                        }
                    }
                    Log.d("OrderActivity", "Current Order Response Body: " + new Gson().toJson(orderCurrentList));
                    if (orderCurrentList.isEmpty()) {
                        Toast.makeText(OrderActivity.this, "Không có đơn hàng ", Toast.LENGTH_SHORT).show();
                    } else {
                        orderAdapter = new OrderAdapter(OrderActivity.this, orderCurrentList);
                        currentOrderRecyclerView.setAdapter(orderAdapter);
                    }
                } else {
                    Log.e("OrderActivity", "Response Error: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
}