package com.example.cafeteriaorderingapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Dto.DelivererOrder;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DelivererInProgressAdapter extends RecyclerView.Adapter<DelivererInProgressAdapter.DeliveryOrderViewHolder> {
    private List<DelivererOrder.DeliveryInProgressOrder> deliveryList;
    private Context context;
    private String[] statusValues;
    private String[] statusDisplays;

    public DelivererInProgressAdapter(Context context, List<DelivererOrder.DeliveryInProgressOrder> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
        this.statusValues = context.getResources().getStringArray(R.array.order_status);
        this.statusDisplays = context.getResources().getStringArray(R.array.order_status_options);
    }

    @NonNull
    @Override
    public DeliveryOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo view cho item của RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_requested_deliverer, parent, false);
        return new DeliveryOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryOrderViewHolder holder, int position) {
        DelivererOrder.DeliveryInProgressOrder delivery = deliveryList.get(position);

        holder.recipientNameValue.setText(delivery.getPatronName());
        holder.recipientPhoneValue.setText(delivery.getNumber());
        holder.orderAddressValue.setText(delivery.getAddress());
        holder.orderTotalAmountValue.setText(String.format("%,.0f VND", delivery.getTotalAmount()));

        // Set trạng thái hiện tại cho Spinner
        int defaultPos = -1;
        for (int i = 0; i < statusValues.length; i++) {
            if (statusValues[i].equals(delivery.getDeliveryStatus())) {
                defaultPos = i;
                break;
            }
        }
        if (defaultPos >= 0) {
            holder.orderStatusSpinner.setSelection(defaultPos);
        }

        // Xử lý Spinner khi chọn trạng thái
        holder.orderStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedApiStatus = statusValues[pos];
                updateOrderStatus(delivery.getOrderId(), selectedApiStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì
            }
        });
    }

    private void updateOrderStatus(int orderId, String newStatus) {
        ApiService apiService = RetrofitClient.getDetailService();
        Call<Void> call = apiService.updateOrderStatus(orderId, newStatus);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                    Log.d("OrderUpdate", "Order " + orderId + " updated to " + newStatus);
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("OrderUpdate", "Failed to update order " + orderId + ": " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OrderUpdate", "API call failed: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryList != null ? deliveryList.size() : 0;
    }

    public void updateDeliveries(List<DelivererOrder.DeliveryInProgressOrder> newDeliveries) {
        this.deliveryList = newDeliveries;
        notifyDataSetChanged();
    }

    static class DeliveryOrderViewHolder extends RecyclerView.ViewHolder {
        TextView recipientNameValue;
        TextView recipientPhoneValue;
        TextView orderAddressValue;
        TextView orderTotalAmountValue;
        Spinner orderStatusSpinner;

        public DeliveryOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            recipientNameValue = itemView.findViewById(R.id.recipientNameValue);
            recipientPhoneValue = itemView.findViewById(R.id.recipientPhoneValue);
            orderAddressValue = itemView.findViewById(R.id.orderAddressValue);
            orderTotalAmountValue = itemView.findViewById(R.id.orderTotalAmountValue);
            orderStatusSpinner = itemView.findViewById(R.id.orderStatusSpinner);
        }
    }
}

