package com.example.cafeteriaorderingapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.example.cafeteriaorderingapp.Adapter.CategoryAdapter;
import com.example.cafeteriaorderingapp.Adapter.MealAdapter;
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
import com.google.gson.Gson;
import com.example.cafeteriaorderingapp.Adapter.MenuAdapter;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private RecyclerView restaurantsView, mealsView;
    private BottomNavigationView bottomMenu;
    private static final String TAG = "API_RESPONSE";
    ApiService recommendService = RetrofitClient.getRecommendService();
    ApiService detailService = RetrofitClient.getDetailService();
    private List<MealDetail> mealDetails = new ArrayList<>(); // List chứa MealDetail
    private MealAdapter mealAdapter;
    private List<RestaurantDetail.Menu> menuList = new ArrayList<>();
    private MenuAdapter menuAdapter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userIdStr  = sharedPreferences.getString("ACCOUNT_ID", null);
        userId = (userIdStr != null) ? Integer.parseInt(userIdStr) : -1;

        initViews();
        initMeals();
        initRestaurants();
        mealAdapter = new MealAdapter(mealDetails);
        mealsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mealsView.setAdapter(mealAdapter);

        Log.d("MealList", "Size: " + mealDetails.size());


        setVariable();
    }

    private void initRestaurants() {
        recommendService.getRecommendedRestaurants(userId).enqueue(new Callback<List<RestaurantRecommendation>>() {
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
        Log.d(TAG, "fetchRestaurantDetail: " + url);
        detailService.getRestaurantDetail(addressId).enqueue(new Callback<RestaurantDetail>() {
            @Override
            public void onResponse(Call<RestaurantDetail> call, Response<RestaurantDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RestaurantDetail restaurantDetail = response.body();

                    Log.d(TAG, "Restaurant Name: " + restaurantDetail.getMenuName());
                    Log.d(TAG, "Menu Id: " + restaurantDetail.getMenuId());

                    // Kiểm tra JSON trả về
                    Log.d(TAG, "Raw JSON Response: " + new Gson().toJson(response.body()));

                    // Lấy danh sách địa chỉ
                    String restaurantImage = null;
                    if (restaurantDetail.getAddresses() != null && !restaurantDetail.getAddresses().isEmpty()) {
                        restaurantImage = restaurantDetail.getAddresses().get(0).getImage(); // Lấy ảnh từ address đầu tiên
                    } else {
                        Log.e(TAG, "Addresses is NULL hoặc trống!");
                    }

                    // Tạo một đối tượng đại diện cho nhà hàng
                    RestaurantDetail.Menu restaurantMenu = new RestaurantDetail.Menu();
                    restaurantMenu.setItemName(restaurantDetail.getMenuName()); // Đặt menuName làm tên nhà hàng
                    restaurantMenu.setImage(restaurantImage); // Đặt ảnh từ address làm ảnh
                    Log.d(TAG, "onResponse: restaurantDetail.getMenus()" + restaurantDetail.getMenus());
                    // Mỗi nhà hàng cần có danh sách món ăn riêng
                    List<RestaurantDetail.Menu> menuItems = new ArrayList<>(restaurantDetail.getMenus());
                    Log.d(TAG, "onResponse: menuItems " + menuItems.toString());
                    // Lưu thông tin nhà hàng + danh sách món ăn
                    restaurantMenu.setMenuList(menuItems);  // Cần thêm getter/setter cho menuList

                    menuList.add(restaurantMenu); // Thêm nhà hàng vào danh sách menu

                    runOnUiThread(() -> {
                        if (menuAdapter == null) {
                            menuAdapter = new MenuAdapter(MainActivity.this, menuList);
                            restaurantsView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            restaurantsView.setAdapter(menuAdapter);
                        } else {
                            menuAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<RestaurantDetail> call, Throwable t) {
                Log.e(TAG, "API Call Failed | URL: " + url, t);
            }
        });
    }


    private void initMeals() {
        Call<List<MealRecommendation>> call = recommendService.getRecommendedMeals(userId); // Hardcoded user_id
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
                    runOnUiThread(() -> mealAdapter.notifyDataSetChanged());
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
        restaurantsView = findViewById(R.id.listRestaurantView);
        mealsView = findViewById(R.id.listMealsView);
        bottomMenu = findViewById(R.id.bottomMenu);
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

    }

