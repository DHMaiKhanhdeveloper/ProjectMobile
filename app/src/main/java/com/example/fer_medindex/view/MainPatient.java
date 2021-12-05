package com.example.fer_medindex.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.fer_medindex.R;
import com.example.fer_medindex.fragment.DoctorFragment;
import com.example.fer_medindex.fragment.ListPatientFragment;

public class MainPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);
        // ẩn thanh trạng thái trong Android
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new ListPatientFragment()).commit();
    }
}