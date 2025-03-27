package com.example.cafeteriaorderingapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Constants.OrderStatus;
import com.example.cafeteriaorderingapp.Dto.Order;
import com.example.cafeteriaorderingapp.R;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private Context context;
    private List<Order> orderList;

    public OrderHistoryAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderTitle.setText(order.getOrderTitle());
        holder.tvOrderDate.setText("Date: " + order.getOrderDate());
        holder.tvOrderStatus.setText("Status: " +  OrderStatus.fromString(order.getStatus()).getDisplayName());
        holder.tvTotalPrice.setText("Total: " + order.getTotalAmount() + " VND");

        // Đổi màu trạng thái
        if (order.getStatus().equalsIgnoreCase("COMPLETED")) {
            holder.tvOrderStatus.setTextColor(Color.GREEN);
        } else {
            holder.tvOrderStatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTitle, tvOrderDate, tvOrderStatus, tvTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTitle = itemView.findViewById(R.id.tvOrderTitle);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
