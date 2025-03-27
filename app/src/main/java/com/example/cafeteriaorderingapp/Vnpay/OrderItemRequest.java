package com.example.cafeteriaorderingapp.Vnpay;

public class OrderItemRequest {
    private int itemId;
    private int quantity;

    public OrderItemRequest(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
}
