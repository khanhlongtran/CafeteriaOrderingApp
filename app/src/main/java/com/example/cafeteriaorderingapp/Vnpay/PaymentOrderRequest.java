package com.example.cafeteriaorderingapp.Vnpay;

public class PaymentOrderRequest {
    private PaymentRequest payment;
    private CreateOrderRequest order;

    public PaymentOrderRequest(PaymentRequest payment, CreateOrderRequest order) {
        this.payment = payment;
        this.order = order;
    }

    public PaymentRequest getPayment() { return payment; }
    public CreateOrderRequest getOrder() { return order; }
}
