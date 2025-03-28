package com.example.cafeteriaorderingapp.Activity.Deliverer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Activity.LoginActivity;
import com.example.cafeteriaorderingapp.Adapter.DelivererInProgressAdapter;
import com.example.cafeteriaorderingapp.Adapter.DelivererOrderAdapter;
import com.example.cafeteriaorderingapp.Dto.DelivererOrder;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDelivererActivity extends AppCompatActivity {
    private RecyclerView recyclerRequestDelivery, recyclerAcceptedOrder;
    private ImageView profile;
    private DelivererOrderAdapter orderAdapter;
    private DelivererInProgressAdapter delivererInProgressAdapter;
    private List<DelivererOrder> orderList;
    private List<DelivererOrder.DeliveryInProgressOrder> orderInProgress;
    private int deliverUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_deliverer);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("ACCOUNT_ID", null);
        deliverUserId = (userIdStr != null) ? Integer.parseInt(userIdStr) : -1;
        profile = findViewById(R.id.imageView6);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeDelivererActivity.this, SettingDelivererActivity.class);
                startActivity(intent);
            }
        });
        recyclerRequestDelivery = findViewById(R.id.recycler_request_delivery);
        recyclerRequestDelivery.setLayoutManager(new LinearLayoutManager(this));
        recyclerAcceptedOrder = findViewById(R.id.recycler_accepted_delivery);
        recyclerAcceptedOrder.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderInProgress = new ArrayList<>();  // Thêm dòng này

        fetchOrders();
        fetchInProgressOrders(deliverUserId);  // Gọi hàm mới

        orderAdapter = new DelivererOrderAdapter(this, orderList);
        recyclerRequestDelivery.setAdapter(orderAdapter);
        delivererInProgressAdapter = new DelivererInProgressAdapter(this, orderInProgress);
        recyclerAcceptedOrder.setAdapter(delivererInProgressAdapter);

        orderAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateRecyclerViewHeight(recyclerRequestDelivery, orderList.size());
            }
        });

        // Cập nhật chiều cao ban đầu
        updateRecyclerViewHeight(recyclerRequestDelivery, orderList.size());
    }

    private void fetchOrders() {
        ApiService detailService = RetrofitClient.getDetailService();
        Call<List<DelivererOrder>> call = detailService.getAllOrders();

        call.enqueue(new Callback<List<DelivererOrder>>() {
            @Override
            public void onResponse(Call<List<DelivererOrder>> call, Response<List<DelivererOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderList = response.body();
                    orderAdapter.updateOrders(orderList);
                    for (DelivererOrder order : orderList) {
                        Log.d("OrderDebug", "Order Details: " +
                                "UserId=" + order.getUserId() +
                                ", FullName=" + order.getFullName() +
                                ", OrderName=" + order.getOrderName() +
                                ", TotalAmount=" + order.getTotalAmount() +
                                ", Address=" + order.getAddressLine() + ", " + order.getCity() + ", " + order.getState() +
                                ", GeoLocation=" + order.getGeoLocation());
                    }
                } else {
                    Toast.makeText(HomeDelivererActivity.this, "No orders found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DelivererOrder>> call, Throwable t) {
                Toast.makeText(HomeDelivererActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchInProgressOrders(int deliverUserId) {
        ApiService detailService = RetrofitClient.getDetailService();
        Call<List<DelivererOrder.DeliveryInProgressOrder>> call = detailService.getOrderInProgressDeliveries(deliverUserId); // Giả định API endpoint

        call.enqueue(new Callback<List<DelivererOrder.DeliveryInProgressOrder>>() {
            @Override
            public void onResponse(Call<List<DelivererOrder.DeliveryInProgressOrder>> call,
                                   Response<List<DelivererOrder.DeliveryInProgressOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderInProgress = response.body();
                    delivererInProgressAdapter = new DelivererInProgressAdapter(
                            HomeDelivererActivity.this,
                            orderInProgress
                    );
                    recyclerAcceptedOrder.setAdapter(delivererInProgressAdapter);

                    // Debug log
                    for (DelivererOrder.DeliveryInProgressOrder order : orderInProgress) {
                        Log.d("InProgressDebug", "In Progress Order Details: " +
                                "OrderId=" + order.getOrderId() +
                                ", PatronName=" + order.getPatronName() +
                                ", Phone=" + order.getNumber() +
                                ", Address=" + order.getAddress() +
                                ", TotalAmount=" + order.getTotalAmount() +
                                ", Status=" + order.getDeliveryStatus());
                    }
                } else {
                    Toast.makeText(HomeDelivererActivity.this,
                            "No in-progress orders found",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DelivererOrder.DeliveryInProgressOrder>> call,
                                  Throwable t) {
                Toast.makeText(HomeDelivererActivity.this,
                        "Error fetching in-progress orders: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("InProgressError", "Failed to fetch in-progress orders: " + t.getMessage());
            }
        });
    }

    private void updateRecyclerViewHeight(RecyclerView recyclerView, int itemCount) {
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = (itemCount > 0) ? dpToPx(300) : 0; // Nếu có dữ liệu: 300dp, nếu không: 0
        recyclerView.setLayoutParams(params);
    }

    // Chuyển đổi dp sang pixel
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}