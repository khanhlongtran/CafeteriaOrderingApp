package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("itemId")
    private int itemId;

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    public OrderItem() {
    }

    public int getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}