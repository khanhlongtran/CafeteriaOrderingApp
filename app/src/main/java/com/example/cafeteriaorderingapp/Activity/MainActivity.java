package com.example.cafeteriaorderingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.example.cafeteriaorderingapp.Adapter.CategoryAdapter;
import com.example.cafeteriaorderingapp.Adapter.SliderAdapter;
import com.example.cafeteriaorderingapp.Domain.Category;
import com.example.cafeteriaorderingapp.Domain.SliderItems;
import com.example.cafeteriaorderingapp.Dto.MealDetail;
import com.example.cafeteriaorderingapp.Dto.MealRecommendation;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.Dto.RestaurantRecommendation;
import com.example.cafeteriaorderingapp.R;
import com.example.cafeteriaorderingapp.Service.ApiService;
import com.example.cafeteriaorderingapp.Service.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private ProgressBar progressBarBanner, progressBarCategory;
    private ViewPager2 viewpager2;
    private RecyclerView restaurantsView, mealsView;
    private BottomNavigationView bottomMenu;
    private static final String TAG = "API_RESPONSE";
    ApiService recommendService = RetrofitClient.getRecommendService();
    ApiService detailService = RetrofitClient.getDetailService();
    private List<MealDetail> mealDetails = new ArrayList<>(); // List chứa MealDetail
    private List<RestaurantDetail> restaurantDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initMeals();
        initRestaurants();
        initBanner();
        setVariable();
    }

    private void initRestaurants() {
        recommendService.getRecommendedRestaurants(35).enqueue(new Callback<List<RestaurantRecommendation>>() {
            @Override
            public void onResponse(Call<List<RestaurantRecommendation>> call, Response<List<RestaurantRecommendation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RestaurantRecommendation> recommendations = response.body();
                    for (RestaurantRecommendation restaurant : recommendations) {
                        Log.d(TAG, "Restaurant: " + restaurant.getRestaurantName() + " " + restaurant.getRestaurantId() +
                                " | Distance: " + restaurant.getDistanceKm() +
                                " km | Score: " + restaurant.getSimilarityScore());
                        fetchRestaurantDetail(restaurant.getRestaurantId());
                    }
                } else {
                    Log.e(TAG, "Failed to fetch restaurant recommendations | HTTP Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantRecommendation>> call, Throwable t) {
                Log.e(TAG, "API Call Failed", t);
            }
        });
    }

    private void fetchRestaurantDetail(int addressId) {
        String url = detailService.getRestaurantDetail(addressId).request().url().toString(); // Lấy URL

        detailService.getRestaurantDetail(addressId).enqueue(new Callback<RestaurantDetail>() {
            @Override
            public void onResponse(Call<RestaurantDetail> call, Response<RestaurantDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RestaurantDetail restaurantDetail = response.body();
                    restaurantDetailList.add(restaurantDetail); // Thêm vào danh sách

                    Log.d(TAG, "Restaurant Name: " + restaurantDetail.getFullName());
                    Log.d(TAG, "Address: " + restaurantDetail.getAddressLine());
                    Log.d(TAG, "Phone: " + restaurantDetail.getPhone());

                    // Log danh sách menu
                    for (RestaurantDetail.Menu menu : restaurantDetail.getMenus()) {
                        Log.d(TAG, "Menu: " + menu.getMenuName());
                        for (RestaurantDetail.MenuItem item : menu.getMenuItems()) {
                            Log.d(TAG, " - " + item.getItemName() + " | Price: " + item.getPrice());
                        }
                    }

                } else {
                    Log.e(TAG, "Failed to fetch restaurant detail | HTTP Code: " + response.code() + " | URL: " + url);
                    try {
                        Log.e(TAG, "Error Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(TAG, "Failed to read error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetail> call, Throwable t) {
                Log.e(TAG, "API Call Failed | URL: " + url, t);
            }
        });
    }


    private void initMeals() {
        Call<List<MealRecommendation>> call = recommendService.getRecommendedMeals(35); // Hardcoded user_id
        call.enqueue(new Callback<List<MealRecommendation>>() {
            @Override
            public void onResponse(Call<List<MealRecommendation>> call, Response<List<MealRecommendation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealRecommendation> meals = response.body();
                    for (MealRecommendation meal : meals) {
                        fetchMealDetail(meal.getItemId());
                    }
                } else {
                    // Log lỗi chi tiết
                    try {
                        String errorBody = response.errorBody().string(); // Lấy lỗi từ response
                        Log.e(TAG, "Response failed: " + response.code() + " - " + response.message());
                        Log.e(TAG, "Error body: " + errorBody);
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing response error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MealRecommendation>> call, Throwable t) {
                Log.e(TAG, "API Call Failed", t);
            }
        });
    }

    private void fetchMealDetail(int itemId) {
        detailService.getMealDetail(itemId).enqueue(new Callback<MealDetail>() {
            @Override
            public void onResponse(Call<MealDetail> call, Response<MealDetail> response) {
                Log.d(TAG, "Calling API: " + call.request().url());
                if (response.isSuccessful() && response.body() != null) {
                    MealDetail mealDetail = response.body();
                    mealDetails.add(mealDetail); // Thêm vào danh sách
                    Log.d(TAG, "✅ Success - Detail: " + mealDetail.getItemName() + " - " + mealDetail.getDescription());
                } else {
                    // Log lỗi từ server (nếu có)
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        Log.e(TAG, "❌ Failed to fetch meal detail for itemId: " + itemId +
                                " | HTTP Code: " + response.code() +
                                " | Error: " + errorBody);
                    } catch (IOException e) {
                        Log.e(TAG, "❌ Error parsing response errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MealDetail> call, Throwable t) {
                Log.e(TAG, "🚨 API Call Failed for itemId: " + itemId + " | Error: " + t.getMessage(), t);
            }
        });
    }


    private void initViews() {
        progressBarBanner = findViewById(R.id.progressBarBanner);
        progressBarCategory = findViewById(R.id.progressBarCategory);
        viewpager2 = findViewById(R.id.viewpager2);
        restaurantsView = findViewById(R.id.listRestaurantView);
        mealsView = findViewById(R.id.listMealsView);
        bottomMenu = findViewById(R.id.bottomMenu);
    }

    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");
        progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    progressBarBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void banners(ArrayList<SliderItems> items) {
        viewpager2.setAdapter(new SliderAdapter(items, viewpager2));
        viewpager2.setClipChildren(false);
        viewpager2.setClipToPadding(false);
        viewpager2.setOffscreenPageLimit(3);
        viewpager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        viewpager2.setPageTransformer(compositePageTransformer);
    }

    private void setVariable() {
        bottomMenu.setSelectedItemId(R.id.home);
        bottomMenu.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.order) {
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                return true;
            } else if (itemId == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (itemId == R.id.setting) {
                startActivity(new Intent(MainActivity.this, SettingAccountActivity.class));
                return true;
            }
            return false;
        });
    }

    // De xuat quan an, mon an => list mon an khi an vao
    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Category.class));
                    }
                    if (!list.isEmpty()) {
                        restaurantsView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        restaurantsView.setAdapter(new CategoryAdapter(list));
                    }
                    progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
