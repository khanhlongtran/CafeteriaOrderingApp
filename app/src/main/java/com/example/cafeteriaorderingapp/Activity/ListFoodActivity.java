package com.example.cafeteriaorderingapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaorderingapp.Adapter.FoodListAdapter;
import com.example.cafeteriaorderingapp.Domain.Foods;
import com.example.cafeteriaorderingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodActivity extends BaseActivity {
    private int categoryId;
    private String categoryName;
    private TextView titleTxt;
    private ImageView backBtn;
    private RecyclerView foodListView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        initViews();
        getIntentExtra();
        initList();
    }

    private void initViews() {
        titleTxt = findViewById(R.id.titleTxt);
        backBtn = findViewById(R.id.backBtn);
        foodListView = findViewById(R.id.foodListView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("CategoryId").equalTo(categoryId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Foods.class));
                    }
                    if (!list.isEmpty()) {
                        foodListView.setLayoutManager(new LinearLayoutManager(ListFoodActivity.this, LinearLayoutManager.VERTICAL, false));
                        foodListView.setAdapter(new FoodListAdapter(list));
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");

        titleTxt.setText(categoryName);
        backBtn.setOnClickListener(v -> finish());
    }
}
