package com.example.cafeteriaorderingapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Activity.Deliverer.HomeDelivererActivity;
import com.example.cafeteriaorderingapp.Dto.DelivererOrder;
import com.example.cafeteriaorderingapp.Dto.Delivery;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;


import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DelivererOrderAdapter extends RecyclerView.Adapter<DelivererOrderAdapter.DelivererOrderViewHolder> {
    private List<DelivererOrder> orderList;
    private Context context;
    private int deliverUserId;

    public DelivererOrderAdapter(Context context, List<DelivererOrder> orderList) {
        this.context = context;
        this.orderList = orderList;

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userIdStr = sharedPreferences.getString("ACCOUNT_ID", null);
        deliverUserId = Integer.parseInt(userIdStr);
    }

    @NonNull
    @Override
    public DelivererOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_deliverer, parent, false);
        return new DelivererOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DelivererOrderViewHolder holder, int position) {
        DelivererOrder order = orderList.get(position);

        holder.recipientNameValue.setText(order.getFullName());
        holder.recipientPhoneValue.setText(order.getNumber());
        holder.orderAddressValue.setText(order.getAddressLine() + ", " + order.getCity() + ", " + order.getState());
        holder.orderTotalAmountValue.setText(String.format("%,.0f VND", order.getTotalAmount()));

        holder.acceptOrderButton.setOnClickListener(v -> {
            // Tạo DeliveryCreateDto với thông tin từ Order
            Delivery.DeliveryCreateDto deliveryCreateDto = new Delivery.DeliveryCreateDto();
            deliveryCreateDto.setOrderId(order.getOrderId());
            deliveryCreateDto.setDeliverUserId(deliverUserId);

            Date currentDate = new Date();
            String formattedDate = Delivery.DeliveryCreateDto.formatToISO8601(currentDate);

            deliveryCreateDto.setPickupTime(formattedDate);
            deliveryCreateDto.setDeliveryTime(formattedDate);
            deliveryCreateDto.setCreatedAt(formattedDate);
            deliveryCreateDto.setUpdatedAt(formattedDate);
            Log.d("API Request", "Sending DeliveryCreateDto: " +
                    "OrderId: " + deliveryCreateDto.getOrderId() + ", " +
                    "DeliverUserId: " + deliveryCreateDto.getDeliverUserId() + ", " +
                    "PickupTime: " + deliveryCreateDto.getPickupTime() + ", " +
                    "DeliveryTime: " + deliveryCreateDto.getDeliveryTime() + ", " +
                    "CreatedAt: " + deliveryCreateDto.getCreatedAt() + ", " +
                    "UpdatedAt: " + deliveryCreateDto.getUpdatedAt());
            // Gọi API để tạo Delivery
            ApiService apiService = RetrofitClient.getDetailService();
            Call<Delivery.DeliveryResponse> call = apiService.createDelivery(deliveryCreateDto);

            call.enqueue(new Callback<Delivery.DeliveryResponse>() {
                @Override
                public void onResponse(Call<Delivery.DeliveryResponse> call, Response<Delivery.DeliveryResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("API Response", "Order Accepted: " + response.body().toString());
                        Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show();
                        Log.d("UpdateStatus", "onResponse: "+order.getOrderId());

                        apiService.updateOrderStatus(order.getOrderId(), "DELIVERY_ACCEPTED")
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Log.d("UpdateStatus", "Order Status Updated: " + order.getOrderId());
                                        } else {
                                            Log.e("UpdateStatus", "Error Code: " + response.code() + ", Message: " + response.message());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.e("UpdateStatus", "Error: " + t.getMessage());
                                    }
                                });
                        Intent intent = new Intent(context, HomeDelivererActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);  // Để không có hiệu ứng chuyển động
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                        Log.e("API Response Error", "Error Code: " + response.code() + ", Message: " + response.message());
                        Toast.makeText(context, "Failed to Accept Order", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Delivery.DeliveryResponse> call, Throwable t) {
                    Log.e("API Call Failure", "Error: " + t.getMessage());
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    public void updateOrders(List<DelivererOrder> newOrders) {
        this.orderList = newOrders;
        notifyDataSetChanged();
    }

    static class DelivererOrderViewHolder extends RecyclerView.ViewHolder {
        TextView recipientNameValue;
        TextView recipientPhoneValue;
        TextView orderAddressValue;
        TextView orderTotalAmountValue;
        Button acceptOrderButton;
        public DelivererOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            recipientNameValue = itemView.findViewById(R.id.recipientNameValue);
            recipientPhoneValue = itemView.findViewById(R.id.recipientPhoneValue);
            orderAddressValue = itemView.findViewById(R.id.orderAddressValue);
            orderTotalAmountValue = itemView.findViewById(R.id.orderTotalAmountValue);
            acceptOrderButton = itemView.findViewById(R.id.acceptOrderButton);
        }
    }

}
