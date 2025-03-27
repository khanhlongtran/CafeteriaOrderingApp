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
import com.example.cafeteriaorderingapp.Activity.ListFoodActivity;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.R;
import com.google.gson.Gson;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private List<RestaurantDetail.Menu> menuList;
    private Context context;

    public MenuAdapter(Context context, List<RestaurantDetail.Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        RestaurantDetail.Menu menuItem = menuList.get(position);
        holder.txtItemName.setText(menuItem.getItemName());

        // Kiểm tra nếu image bị null thì hiển thị ảnh mặc định
        if (menuItem.getImage() != null && !menuItem.getImage().isEmpty()) {
            Glide.with(context).load(menuItem.getImage()).into(holder.imgItem);
        } else {
            holder.imgItem.setImageResource(R.drawable.white_bg); // Ảnh mặc định
        }
        // Bắt sự kiện click vào nhà hàng
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListFoodActivity.class);
            intent.putExtra("restaurantName", menuItem.getItemName());
            Log.d("MenuAdapter", "onBindViewHolder: " + menuItem.getMenuList());
            intent.putExtra("menuList", new Gson().toJson(menuItem.getMenuList()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        ImageView imgItem;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            imgItem = itemView.findViewById(R.id.imgItem);
        }
    }
}
