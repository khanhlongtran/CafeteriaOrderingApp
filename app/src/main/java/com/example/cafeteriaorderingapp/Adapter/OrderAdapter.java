package com.example.cafeteriaorderingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Constants.OrderStatus;
import com.example.cafeteriaorderingapp.Dto.Order;
import com.example.cafeteriaorderingapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_current_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderTitle.setText(order.getOrderTitle());
        holder.orderPrice.setText("Total" + order.getTotalAmount()+" VND");
        holder.orderStatus.setText("Status: " + OrderStatus.fromString(order.getStatus()).getDisplayName());
        holder.orderTime.setText("Time: " + order.getOrderDate());
        holder.orderRating.setRating(3.0f); // Đặt giá trị mặc định, bạn có thể thay đổi nếu có rating từ API.
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderTitle, orderPrice, orderStatus, orderTime;
        public RatingBar orderRating;

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderTitle = itemView.findViewById(R.id.orderTitle);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderTime = itemView.findViewById(R.id.orderTime);
            orderRating = itemView.findViewById(R.id.orderRating);
        }
    }
}
