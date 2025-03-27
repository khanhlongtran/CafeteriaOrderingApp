package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Order {
    @SerializedName("orderId")
    private int orderId;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("status")
    private String status;

    @SerializedName("paymentMethod")
    private String paymentMethod;

    @SerializedName("totalAmount")
    private double totalAmount;

    @SerializedName("addressId")
    private int addressId;

    @SerializedName("orderTitle")
    private String orderTitle;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("orderItems")
    private List<OrderItem> orderItems;

    @SerializedName("address")
    private Address address; // Đối tượng Address riêng

    @SerializedName("deliveries")
    private List<Delivery> deliveries; // Nếu có thể null, để List rỗng

    @SerializedName("feedbacks")
    private List<Feedback> feedbacks; // Nếu có thể null, để List rỗng

    public Order() {
    }

    // Getter methods
    public int getOrderId() { return orderId; }
    public String getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getTotalAmount() { return totalAmount; }
    public int getAddressId() { return addressId; }
    public String getOrderTitle() { return orderTitle; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public Address getAddress() { return address; }
    public List<Delivery> getDeliveries() { return deliveries; }
    public List<Feedback> getFeedbacks() { return feedbacks; }
}

