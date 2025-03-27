package com.example.cafeteriaorderingapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.cafeteriaorderingapp.Activity.DetailActivity;
import com.example.cafeteriaorderingapp.Domain.Foods;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {
    private List<RestaurantDetail.Menu> foodList;
    private Context context;

    public FoodListAdapter(Context context, List<RestaurantDetail.Menu> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        RestaurantDetail.Menu foodItem = foodList.get(position);
        holder.txtFoodName.setText(foodItem.getItemName());
        holder.txtFoodPrice.setText(String.format(Locale.getDefault(), "%,.0f VND", foodItem.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FoodListAdapter", "onBindViewHolder: " + foodItem.toString());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("foodItem", foodItem); // Truyền object qua Intent
                context.startActivity(intent);
            }
        });
        if (foodItem.getImage() != null && !foodItem.getImage().isEmpty()) {
            Glide.with(context).load(foodItem.getImage()).into(holder.imgFood);
        } else {
            holder.imgFood.setImageResource(R.drawable.white_bg);
        }
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView txtFoodName, txtFoodPrice;
        ImageView imgFood;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            imgFood = itemView.findViewById(R.id.imgFood);
        }
    }
}
