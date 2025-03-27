package com.example.cafeteriaorderingapp.Vnpay;

public class PaymentRequest {
    private String orderInfo;
    private String bankCode;
    private String returnUrl;
    public PaymentRequest(String orderInfo, String bankCode, String returnUrl) {
        this.orderInfo = orderInfo;
        this.bankCode = bankCode;
        this.returnUrl = returnUrl;
    }

    public String getOrderInfo() { return orderInfo; }
    public String getBankCode() { return bankCode; }

    public String getReturnUrl() {
        return returnUrl;
    }
}
