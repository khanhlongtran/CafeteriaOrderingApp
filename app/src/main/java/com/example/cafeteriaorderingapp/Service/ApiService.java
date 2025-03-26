package com.example.cafeteriaorderingapp.Service;

import com.example.cafeteriaorderingapp.Dto.MealDetail;
import com.example.cafeteriaorderingapp.Dto.MealRecommendation;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.Dto.RestaurantRecommendation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("recommendMeals")
    Call<List<MealRecommendation>> getRecommendedMeals(@Query("user_id") int userId);

    @GET("api/Patron/item/{itemId}")
    Call<MealDetail> getMealDetail(@Path("itemId") int itemId);

    @GET("recommendRestaurants")
    Call<List<RestaurantRecommendation>> getRecommendedRestaurants(@Query("user_id") int userId);

    @GET("api/Patron/restaurant/{addressId}")
    Call<RestaurantDetail> getRestaurantDetail(@Path("addressId") int addressId);

}

