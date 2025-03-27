package com.example.cafeteriaorderingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Adapter.CartAdapter;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.Dto.UserAddressCuisineResponse;
import com.example.cafeteriaorderingapp.Helper.ManagmentCart;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;
import com.example.cafeteriaorderingapp.Vnpay.CreateOrderRequest;
import com.example.cafeteriaorderingapp.Vnpay.OrderItemRequest;
import com.example.cafeteriaorderingapp.Vnpay.PaymentOrderRequest;
import com.example.cafeteriaorderingapp.Vnpay.PaymentRequest;
import com.example.cafeteriaorderingapp.Vnpay.PaymentVNPAYResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {
    private ManagmentCart managmentCart;
    private TextView emptyTxt, totalFeeTxt, taxTxt, deliveryTxt, totalTxt;
    private ScrollView scrollViewCart;
    private RecyclerView cartView;
    private ImageView backBtn;
    private AppCompatButton checkoutBtn;
    private ApiService detailService;
    private int userId, addressId;
    private List<OrderItemRequest> orderItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri data = intent.getData();
            if (data != null) {
                String host = data.getHost();
                if ("payment-success".equals(host)) {
                    Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

                    // Xóa giỏ hàng
                    new ManagmentCart(this).removeAllItems();
                } else if ("payment-failed".equals(host)) {
                    Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        detailService = RetrofitClient.getDetailService();
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userIdStr  = sharedPreferences.getString("ACCOUNT_ID", null);
        userId = (userIdStr != null) ? Integer.parseInt(userIdStr) : -1;

        fetchDefaultAddressId(userId);

        checkoutBtn = findViewById(R.id.checkOutBtn);
        checkoutBtn.setEnabled(true);

        managmentCart = new ManagmentCart(this);

        initViews();
        setVariable();
        calculateCart();
        initCartList();

        checkoutBtn.setOnClickListener(v -> {
            if (userId != -1 && addressId != -1) {
                initiatePayment(userId, addressId, orderItems);
            } else {
                Toast.makeText(CartActivity.this, "Đang tải dữ liệu, vui lòng đợi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollViewCart = findViewById(R.id.scrollViewCart);
        cartView = findViewById(R.id.cartView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        backBtn = findViewById(R.id.backBtn);
    }

    private void initCartList() {
        if (managmentCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollViewCart.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollViewCart.setVisibility(View.VISIBLE);
        }
        cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), managmentCart, this::calculateCart));
    }

    private void calculateCart() {
        double percentTax = 0;  // 2% tax
        double delivery = 0;  // 10 Dollar
        double tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;
        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        totalFeeTxt.setText("$" + itemTotal);
        taxTxt.setText("$" + tax);
        deliveryTxt.setText("$" + delivery);
        totalTxt.setText("$" + total);
    }

    private void setVariable() {
        backBtn.setOnClickListener(v -> startActivity(new Intent(CartActivity.this, MainActivity.class)));
    }

    private void initiatePayment(int userId, int addressId, List<OrderItemRequest> orderItems) {
        // Giả lập danh sách sản phẩm
        //List<OrderItemRequest> orderItems = new ArrayList<>();

        for (RestaurantDetail.Menu item : managmentCart.getListCart()) {
            orderItems.add(new OrderItemRequest(item.getItemId(), item.getNumberInCart()));
        }

        Log.d("CartActivityPay", "UserId: " + userId);
        Log.d("CartActivityPay", "AddressId: " + addressId);
        Log.d("CartActivityPay", "OrderItems: " + new Gson().toJson(orderItems));
        Log.d("CartActivityPay", "Lỗi: " + userId + " " + addressId + " " + orderItems.toString());
        // Tạo yêu cầu đặt hàng
        CreateOrderRequest orderRequest = new CreateOrderRequest(userId, addressId, orderItems);

        // Thông tin thanh toán
        PaymentRequest paymentRequest = new PaymentRequest("Thanhtoan", "VNPAY", "http://192.168.1.252:5110/api/Checkout/vnpay/callback");

        // Gói dữ liệu vào PaymentOrderRequest
        PaymentOrderRequest request = new PaymentOrderRequest(paymentRequest, orderRequest);

        // Gửi yêu cầu
        detailService.createVnpayPayment(request).enqueue(new Callback<PaymentVNPAYResponse>() {
            @Override
            public void onResponse(Call<PaymentVNPAYResponse> call, Response<PaymentVNPAYResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PaymentVNPAYResponse paymentResponse = response.body();
                    if (paymentResponse.isSuccess()) {
                        String paymentUrl = paymentResponse.getPaymentUrl();
                        openPaymentPage(paymentUrl);
                    } else {
                        Toast.makeText(CartActivity.this, "Lỗi: " + paymentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CartActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentVNPAYResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDefaultAddressId(int userId) {
        Call<UserAddressCuisineResponse> call = detailService.getDefaultAddress(userId);
        Log.d("TAG", "Lấy API: " + call.request().url());
        detailService.getDefaultAddress(userId).enqueue(new Callback<UserAddressCuisineResponse>() {
            @Override
            public void onResponse(Call<UserAddressCuisineResponse> call, Response<UserAddressCuisineResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addressId = response.body().getAddressId();
                    Log.d("TAG", "Lấy Default AddressId thành công: " + addressId);
                    checkoutBtn.setEnabled(true); // Cho phép thanh toán khi có địa chỉ hợp lệ
                } else {
                    Log.e("TAG", "Lỗi khi lấy địa chỉ mặc định: " + response.code());
                    Toast.makeText(CartActivity.this, "Không tìm thấy địa chỉ mặc định!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAddressCuisineResponse> call, Throwable t) {
                Log.e("TAG", "Lỗi kết nối khi lấy địa chỉ mặc định: " + t.getMessage());
                Toast.makeText(CartActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
      }
    private void openPaymentPage(String paymentUrl) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
        startActivity(browserIntent);
    }
}
