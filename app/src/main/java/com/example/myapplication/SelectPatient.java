package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);
        ImageView imageViewDoctor = findViewById(R.id.Register_Background_Patient);
        imageViewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPatient.this,Register_Patient.class);
                startActivity(intent);

            }
        });
        ImageView imageViewPatient = findViewById(R.id.xem_lai_form);
        imageViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPatient.this,ProfilePatient.class);
                startActivity(intent);
            }
        });
    }
}