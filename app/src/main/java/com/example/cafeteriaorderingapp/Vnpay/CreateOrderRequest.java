package com.example.cafeteriaorderingapp.Vnpay;

import java.util.List;

public class CreateOrderRequest {
    private int userId;
    private int addressId;
    private List<OrderItemRequest> orderItems;

    public CreateOrderRequest(int userId, int addressId, List<OrderItemRequest> orderItems) {
        this.userId = userId;
        this.addressId = addressId;
        this.orderItems = orderItems;
    }

    public int getUserId() { return userId; }
    public int getAddressId() { return addressId; }
    public List<OrderItemRequest> getOrderItems() { return orderItems; }
}
