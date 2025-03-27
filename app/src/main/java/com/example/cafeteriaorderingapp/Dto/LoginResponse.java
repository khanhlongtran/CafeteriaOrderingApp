package com.example.cafeteriaorderingapp.Dto;

public class LoginResponse {
    private String token;
    private String role;
    private String accountId;

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public String getAccountId() {
        return accountId;
    }
}
