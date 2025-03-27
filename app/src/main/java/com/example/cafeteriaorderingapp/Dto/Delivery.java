package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Delivery {
    @SerializedName("deliveryId")
    private int deliveryId;

    @SerializedName("status")
    private String status;

    // Các trường khác nếu có

    public int getDeliveryId() { return deliveryId; }
    public String getStatus() { return status; }

    public static class DeliveryCreateDto {
        private int orderId;
        private int deliverUserId;
        private String pickupTime;
        private String deliveryTime;
        private String createdAt;
        private String updatedAt;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getDeliverUserId() {
            return deliverUserId;
        }

        public void setDeliverUserId(int deliverUserId) {
            this.deliverUserId = deliverUserId;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        // Method to convert Date to ISO 8601 String format
        public static String formatToISO8601(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return sdf.format(date);
        }
    }
    public class DeliveryResponse {
        private int deliveryId;
        private int orderId;
        private int deliverUserId;
        private String deliveryStatus;
        private Date pickupTime;
        private Date deliveryTime;

        public int getDeliverUserId() {
            return deliverUserId;
        }

        public void setDeliverUserId(int deliverUserId) {
            this.deliverUserId = deliverUserId;
        }

        public int getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(int deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        public Date getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Date deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public Date getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(Date pickupTime) {
            this.pickupTime = pickupTime;
        }
    }
}
