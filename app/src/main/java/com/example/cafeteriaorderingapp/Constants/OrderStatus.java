package com.example.cafeteriaorderingapp.Constants;

public enum OrderStatus {
    REQUEST_DELIVERY,  // after cafeteria request delivery
    DELIVERY_ACCEPTED,  // after deliverer accepted the order
    DELIVERY_IN_PROGRESS,  // after deliverer picked up the order
    COMPLETED,  // after deliverer delivered the order and customer paid (optional)
    CANCELED;

    // Phương thức nhận vào một chuỗi và so sánh
    public static OrderStatus fromString(String status) {
        if (status != null) {
            for (OrderStatus orderStatus : OrderStatus.values()) {
                if (status.equalsIgnoreCase(orderStatus.name())) {
                    return orderStatus;
                }
            }
        }
        return null;  // Trả về null nếu không tìm thấy trạng thái khớp
    }

    // Phương thức trả về tên hiển thị dễ hiểu
    public String getDisplayName() {
        switch (this) {
            case REQUEST_DELIVERY:
                return "Đang yêu cầu giao hàng";
            case DELIVERY_ACCEPTED:
                return "Đã chấp nhận giao hàng";
            case DELIVERY_IN_PROGRESS:
                return "Đang giao hàng";
            case COMPLETED:
                return "Đã hoàn thành";
            case CANCELED:
                return "Đã hủy";
            default:
                return "Trạng thái không xác định";
        }
    }
}
