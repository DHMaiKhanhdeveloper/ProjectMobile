package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashBackground extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    ImageView BackgroundSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_background);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lottieAnimationView = findViewById(R.id.lottie_layer_name);
        BackgroundSplash = findViewById(R.id.background_splash);

        lottieAnimationView.animate().translationY(-2500).setDuration(1000).setStartDelay(5000);
        BackgroundSplash.animate().translationY(1500).setDuration(1000).setStartDelay(5000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashBackground.this,Background.class));
            }
        },5000);
    }
}