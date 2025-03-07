package com.example.cafeteriaorderingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeteriaorderingapp.R;


public class IntroActivity extends BaseActivity {
    private ImageView goBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        initViews();
        setVariable();
    }

    private void initViews() {
        goBtn = findViewById(R.id.goBtn);
    }

    private void setVariable() {
        goBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, MainActivity.class)));
    }
}