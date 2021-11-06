package com.example.fer_medindex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fer_medindex.fragment.DoctorFragment;
import com.example.fer_medindex.fragment.ListPatientFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class BackgroundDoctor extends AppCompatActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_doctor);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DoctorFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.bottom_logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(BackgroundDoctor.this);
            builder.setCancelable(true);

            builder.setNegativeButton("YES", (dialog, which) -> finish());
            builder.setPositiveButton("NO", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @SuppressWarnings("deprecation")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.bottom_doctor:
                        selectedFragment = new DoctorFragment();
                        break;
                    case R.id.bottom_list_patient:
                        selectedFragment = new ListPatientFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        Objects.requireNonNull(selectedFragment)).commit();

                return true;
            };
}