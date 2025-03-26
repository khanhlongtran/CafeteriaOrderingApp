package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

public class MealRecommendation {
    @SerializedName("distance_km")
    private double distanceKm;

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("menu_id")
    private int menuId;

    @SerializedName("menu_name")
    private String menuName;

    @SerializedName("price")
    private int price;

    @SerializedName("restaurant_id")
    private int restaurantId;

    @SerializedName("restaurant_name")
    private String restaurantName;

    @SerializedName("similarity_score")
    private double similarityScore;

    // Getters
    public double getDistanceKm() {
        return distanceKm;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }
}
