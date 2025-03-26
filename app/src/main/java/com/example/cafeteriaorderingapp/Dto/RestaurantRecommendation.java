package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

public class RestaurantRecommendation {
    @SerializedName("restaurant_id")
    private int restaurantId;

    @SerializedName("restaurant_name")
    private String restaurantName;

    @SerializedName("distance_km")
    private double distanceKm;

    @SerializedName("similarity_score")
    private double similarityScore;

    // Constructor
    public RestaurantRecommendation(int restaurantId, String restaurantName, double distanceKm, double similarityScore) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.distanceKm = distanceKm;
        this.similarityScore = similarityScore;
    }

    // Getters and Setters
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }
}

