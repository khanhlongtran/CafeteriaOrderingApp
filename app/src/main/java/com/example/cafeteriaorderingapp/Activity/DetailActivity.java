package com.example.cafeteriaorderingapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.cafeteriaorderingapp.Domain.Foods;
import com.example.cafeteriaorderingapp.Dto.RestaurantDetail;
import com.example.cafeteriaorderingapp.Helper.ManagmentCart;
import com.example.cafeteriaorderingapp.R;

import java.util.Locale;

public class DetailActivity extends BaseActivity {
    private RestaurantDetail.Menu foodItem;
    private int num = 1;
    private ManagmentCart managmentCart;
    private ImageView pic, backBtn;
    private TextView priceTxt, titleTxt, descriptionTxt,  totalTxt, numTxt;
    private Button addBtn;
    private TextView plusBtn, minusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getIntentExtra();
        initViews();
        setVariable();
    }

    private void initViews() {
        backBtn = findViewById(R.id.backBtn);
        pic = findViewById(R.id.pic);
        priceTxt = findViewById(R.id.price);
        titleTxt = findViewById(R.id.itemName);
        descriptionTxt = findViewById(R.id.description);
        totalTxt = findViewById(R.id.totalTxt);
        numTxt = findViewById(R.id.numTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        addBtn = findViewById(R.id.addBtn);
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        backBtn.setOnClickListener(v -> finish());
        Log.d("DetailActivity", foodItem.toString());
        // Load ảnh
        Glide.with(this)
                .load(foodItem.getImage())
                .transform(new CenterCrop(), new RoundedCorners(60))
                .into(pic);

        // Hiển thị thông tin món ăn
        titleTxt.setText(foodItem.getItemName());
        priceTxt.setText(String.format(Locale.getDefault(), "%,.0f VND", foodItem.getPrice()));
        descriptionTxt.setText(foodItem.getDescription());
        totalTxt.setText(String.format(Locale.getDefault(), "%,.0f VND", foodItem.getPrice() * num));

        plusBtn.setOnClickListener(v -> {
            num++;
            numTxt.setText(String.valueOf(num));
            totalTxt.setText(String.format(Locale.getDefault(), "%,.0f VND", foodItem.getPrice() * num));
        });

        minusBtn.setOnClickListener(v -> {
            if (num > 1) {
                num--;
                numTxt.setText(String.valueOf(num));
                totalTxt.setText(String.format(Locale.getDefault(), "%,.0f VND", foodItem.getPrice() * num));
            }
        });

        addBtn.setOnClickListener(v -> {
            foodItem.setNumberInCart(num);
            managmentCart.insertFood(foodItem);
        });
    }

    private void getIntentExtra() {
        foodItem = (RestaurantDetail.Menu) getIntent().getSerializableExtra("foodItem");
    }
}
