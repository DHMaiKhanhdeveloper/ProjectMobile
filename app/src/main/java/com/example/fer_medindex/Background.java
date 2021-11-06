package com.example.fer_medindex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Background extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        ImageView imageViewDoctor = findViewById(R.id.imageView_doctor);
        imageViewDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(Background.this,LoginActivity.class);
            startActivity(intent);

        });
        ImageView imageViewPatient = findViewById(R.id.imageView_patient);
        imageViewPatient.setOnClickListener(v -> {
            Intent intent = new Intent(Background.this,SelectPatient.class);
            startActivity(intent);
        });


    }
}