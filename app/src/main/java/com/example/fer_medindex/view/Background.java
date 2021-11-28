package com.example.fer_medindex.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fer_medindex.R;
import com.google.firebase.auth.FirebaseAuth;

public class Background extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(Background.this, BackgroundDoctor.class));
            finish(); // đóng LoginActivity
            // mở hồ sơ người dùng
            return;
        }
        setContentView(R.layout.activity_background);

        ImageView imageViewDoctor = findViewById(R.id.imageView_doctor);
        imageViewDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(Background.this, LoginActivity.class);
            startActivity(intent);
        });
        ImageView imageViewPatient = findViewById(R.id.imageView_patient);
        imageViewPatient.setOnClickListener(v -> {
            Intent intent = new Intent(Background.this, SelectPatient.class);
            startActivity(intent);
        });
    }
}