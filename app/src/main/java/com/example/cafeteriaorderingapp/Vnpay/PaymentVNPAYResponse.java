package com.example.cafeteriaorderingapp.Vnpay;

public class PaymentVNPAYResponse {
    private String paymentUrl;
    private String message;
    private boolean success;

    public String getPaymentUrl() { return paymentUrl; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
}
