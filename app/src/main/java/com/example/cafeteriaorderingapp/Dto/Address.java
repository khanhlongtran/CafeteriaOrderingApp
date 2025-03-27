package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("addressLine")
    private String addressLine;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("zipCode")
    private String zipCode;

    public Address() {
    }

    public String getAddressLine() { return addressLine; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZipCode() { return zipCode; }
}




