package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

public class DelivererOrder {
    @SerializedName("userId")
    private int userId;  // Thay String thành int vì trong JSON mẫu là số
    @SerializedName("orderId")
    private int orderId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("number")
    private String number;
    @SerializedName("orderName")
    private String orderName;

    @SerializedName("totalAmount")
    private double totalAmount;

    @SerializedName("addressId")
    private int addressId;

    @SerializedName("addressLine")
    private String addressLine;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("geoLocation")
    private String geoLocation;

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getOrderName() { return orderName; }
    public void setOrderName(String orderName) { this.orderName = orderName; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }
    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getGeoLocation() { return geoLocation; }
    public void setGeoLocation(String geoLocation) { this.geoLocation = geoLocation; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public class DeliveryInProgressOrder {
        @SerializedName("deliveryId")
        private int deliveryId;

        @SerializedName("orderId")
        private int orderId;

        @SerializedName("deliverUserId")
        private int deliverUserId;

        @SerializedName("orderName")
        private String orderName;

        @SerializedName("totalAmount")
        private double totalAmount;

        @SerializedName("address")
        private String address;

        @SerializedName("patronName")
        private String patronName;
        @SerializedName("number")
        private String number;
        @SerializedName("pickupTime")
        private String pickupTime;

        @SerializedName("deliveryTime")
        private String deliveryTime;

        @SerializedName("deliveryStatus")
        private String deliveryStatus;

        @SerializedName("createdAt")
        private String createdAt;

        @SerializedName("updatedAt")
        private String updatedAt;

        // Getters
        public int getDeliveryId() { return deliveryId; }
        public int getOrderId() { return orderId; }
        public int getDeliverUserId() { return deliverUserId; }
        public String getOrderName() { return orderName; }
        public double getTotalAmount() { return totalAmount; }
        public String getAddress() { return address; }
        public String getPatronName() { return patronName; }
        public String getPickupTime() { return pickupTime; }
        public String getDeliveryTime() { return deliveryTime; }
        public String getDeliveryStatus() { return deliveryStatus; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }

        // Setters
        public void setDeliveryId(int deliveryId) { this.deliveryId = deliveryId; }
        public void setOrderId(int orderId) { this.orderId = orderId; }
        public void setDeliverUserId(int deliverUserId) { this.deliverUserId = deliverUserId; }
        public void setOrderName(String orderName) { this.orderName = orderName; }
        public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
        public void setAddress(String address) { this.address = address; }
        public void setPatronName(String patronName) { this.patronName = patronName; }
        public void setPickupTime(String pickupTime) { this.pickupTime = pickupTime; }
        public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }
        public void setDeliveryStatus(String deliveryStatus) { this.deliveryStatus = deliveryStatus; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
        public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
