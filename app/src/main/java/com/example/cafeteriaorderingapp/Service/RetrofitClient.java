package com.example.cafeteriaorderingapp.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitRecommend = null;
    private static Retrofit retrofitMealDetail = null;

    public static ApiService getRecommendService() {
        if (retrofitRecommend == null) {
            retrofitRecommend = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.252:5120/") // API recommend
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitRecommend.create(ApiService.class);
    }

    public static ApiService getDetailService() {
        if (retrofitMealDetail == null) {
            retrofitMealDetail = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.252:5110/") // API meal detail
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitMealDetail.create(ApiService.class);
    }
}

