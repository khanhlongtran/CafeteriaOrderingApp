package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class UserAddressCuisineResponse {
    @SerializedName("addressId")
    private int addressId;

    @SerializedName("addressLine")
    private String addressLine;

    @SerializedName("city")
    private String city;

    @SerializedName("defaultCuisine")
    private String defaultCuisine;

    @SerializedName("state")
    private String state;

    @SerializedName("zipCode")
    private String zipCode;

    @SerializedName("isDefault")
    private Boolean isDefault;  // Dùng Boolean thay vì boolean

    @SerializedName("geoLocation")
    private String geoLocation;

    @SerializedName("createdAt")
    private String createdAt;  // Dùng String nếu cần format lại

    @SerializedName("updatedAt")
    private String updatedAt;

    // Getters & Setters
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }

    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDefaultCuisine() { return defaultCuisine; }
    public void setDefaultCuisine(String defaultCuisine) { this.defaultCuisine = defaultCuisine; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public Boolean getIsDefault() { return isDefault; }
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }

    public String getGeoLocation() { return geoLocation; }
    public void setGeoLocation(String geoLocation) { this.geoLocation = geoLocation; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
