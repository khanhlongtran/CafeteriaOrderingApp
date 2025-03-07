package com.example.cafeteriaorderingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.cafeteriaorderingapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ProgressBar progressBarBanner, progressBarCategory;
    private ViewPager2 viewpager2;
    private RecyclerView categoryView;
    private BottomNavigationView bottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initCategory();
        initBanner();
        setVariable();
    }

    private void initViews() {
        progressBarBanner = findViewById(R.id.progressBarBanner);
        progressBarCategory = findViewById(R.id.progressBarCategory);
        viewpager2 = findViewById(R.id.viewpager2);
        categoryView = findViewById(R.id.categoryView);
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
            if (item.getItemId() == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            }
            return false;
        });
    }

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
                        categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                        categoryView.setAdapter(new CategoryAdapter(list));
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
