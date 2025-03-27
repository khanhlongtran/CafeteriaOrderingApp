package com.example.cafeteriaorderingapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cafeteriaorderingapp.Adapter.FoodListAdapter;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListFoodActivity extends AppCompatActivity {
    private RecyclerView foodRecyclerView;
    private FoodListAdapter foodListAdapter;
    private List<RestaurantDetail.Menu> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        foodRecyclerView = findViewById(R.id.foodRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Nhận dữ liệu từ Intent
        String restaurantName = getIntent().getStringExtra("restaurantName");
        String menuJson = getIntent().getStringExtra("menuList");

        if (menuJson != null) {
            Type listType = new TypeToken<List<RestaurantDetail.Menu>>() {}.getType();
            foodList = new Gson().fromJson(menuJson, listType);
        }

        // Cập nhật RecyclerView
        foodListAdapter = new FoodListAdapter(this, foodList);
        foodRecyclerView.setAdapter(foodListAdapter);

        // Hiển thị tên nhà hàng trên thanh tiêu đề
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(restaurantName);
        }
    }
}
