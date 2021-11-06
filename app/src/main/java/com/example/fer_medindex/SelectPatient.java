package com.example.fer_medindex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);
        ImageView imageViewDoctor = findViewById(R.id.Register_Background_Patient);
        imageViewDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(SelectPatient.this,Register_Patient.class);
            startActivity(intent);

        });
        ImageView imageViewPatient = findViewById(R.id.xem_lai_form);
        imageViewPatient.setOnClickListener(v -> {
            Intent intent = new Intent(SelectPatient.this,ProfilePatient.class);
            startActivity(intent);
        });
    }
}