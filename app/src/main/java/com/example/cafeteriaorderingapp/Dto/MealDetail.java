package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MealDetail implements Serializable {
    @SerializedName("itemId")
    private int itemId;

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private int price;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private boolean status;

    @SerializedName("image")
    private String image;

    // Getters
    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public boolean isStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }
}
