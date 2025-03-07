package com.example.cafeteriaorderingapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.cafeteriaorderingapp.Domain.Foods;
import com.example.cafeteriaorderingapp.Helper.ManagmentCart;
import com.example.cafeteriaorderingapp.R;

public class DetailActivity extends BaseActivity {
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;
    private ImageView pic, backBtn;
    private TextView priceTxt, titleTxt, descriptionTxt, ratingTxt, totalTxt, numTxt;
    private RatingBar ratingBar;
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
        priceTxt = findViewById(R.id.priceTxt);
        titleTxt = findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
        ratingBar = findViewById(R.id.ratingBar);
        totalTxt = findViewById(R.id.totalTxt);
        numTxt = findViewById(R.id.numTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        addBtn = findViewById(R.id.addBtn);
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        backBtn.setOnClickListener(v -> finish());

        Glide.with(this)
                .load(object.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(60))
                .into(pic);

        priceTxt.setText("$" + object.getPrice());
        titleTxt.setText(object.getTitle());
        descriptionTxt.setText(object.getDescription());
        ratingTxt.setText(object.getStar() + " Rating");
        ratingBar.setRating((float) object.getStar());
        totalTxt.setText((num * object.getPrice()) + "$");

        plusBtn.setOnClickListener(v -> {
            num++;
            numTxt.setText(String.valueOf(num));
            totalTxt.setText("$" + (num * object.getPrice()));
        });

        minusBtn.setOnClickListener(v -> {
            if (num > 1) {
                num--;
                numTxt.setText(String.valueOf(num));
                totalTxt.setText("$" + (num * object.getPrice()));
            }
        });

        addBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}
