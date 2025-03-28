package com.example.cafeteriaorderingapp.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.cafeteriaorderingapp.Dto.MealDetail;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.R;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private Context context;
    private List<MealDetail> mealList;

    public MealAdapter(List<MealDetail> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_list_food, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealDetail meal = mealList.get(position);

        // Hiển thị tên món ăn
        holder.titleTxt.setText(meal.getItemName());

        // Hiển thị giá với định dạng "VND"
        holder.priceTxt.setText(String.format(Locale.getDefault(), "%,.0f VND", (double) meal.getPrice()));

        // Hiển thị rating mặc định
        holder.rateTxt.setText("5");

        // Load hình ảnh món ăn (không kiểm tra ảnh mặc định)
        Glide.with(context)
                .load(meal.getImage())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(holder.pic);

        // Sự kiện click để xem chi tiết món ăn
        holder.itemView.setOnClickListener(v -> {
            RestaurantDetail.Menu menu = new RestaurantDetail.Menu();
            menu.setItemId(meal.getItemId());
            menu.setItemName(meal.getItemName());
            menu.setDescription(meal.getDescription());
            menu.setPrice(meal.getPrice());
            menu.setItemType(meal.getType());
            menu.setStatus(meal.isStatus());
            menu.setImage(meal.getImage());

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("foodItem", menu); // Truyền `RestaurantDetail.Menu` thay vì `MealDetail`
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt, rateTxt;
        ImageView pic;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            rateTxt = itemView.findViewById(R.id.ratingTxt);
            pic = itemView.findViewById(R.id.img);
        }
    }
}
