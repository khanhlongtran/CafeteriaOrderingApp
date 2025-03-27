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
import com.example.cafeteriaorderingapp.R;

import java.io.Serializable;
import java.util.List;

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

        holder.titleTxt.setText(meal.getItemName());
        holder.priceTxt.setText("$" + meal.getPrice());
        holder.rateTxt.setText(String.valueOf("5"));

        Glide.with(context)
                .load(meal.getImage())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("meal",(Serializable) meal);
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
