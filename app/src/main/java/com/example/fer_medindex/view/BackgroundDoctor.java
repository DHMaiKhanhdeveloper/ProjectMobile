package com.example.fer_medindex.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fer_medindex.R;
import com.example.fer_medindex.fragment.DoctorFragment;
import com.example.fer_medindex.fragment.ListPatientFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class BackgroundDoctor extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_doctor);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DoctorFragment()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.bottom_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit App");
            builder.setMessage("Thoát");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id== R.id.bottom_logout){
//            AlertDialog.Builder builder = new AlertDialog.Builder(BackgroundDoctor.this);
//            builder.setCancelable(true);
//
//            builder.setNegativeButton("YES", (dialog, which) -> finish());
//            builder.setPositiveButton("NO", (dialog, which) -> dialog.cancel());
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        }
//        return true;
//    }

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
                    case R.id.bottom_logout: {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(BackgroundDoctor.this);
//                            builder.setCancelable(true);
//
//                            builder.setNegativeButton("YES", (dialog, which) -> finish());
//                            builder.setPositiveButton("NO", (dialog, which) -> dialog.cancel());
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//                            break;
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Exit App");
                        builder.setMessage("Thoát");
                        builder.setPositiveButton("YES", (dialog, which) -> {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(BackgroundDoctor.this, Background.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        });
                        builder.setNegativeButton("NO", (dialog, which) -> dialog.cancel());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return false;
                    }

                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
                return true;
            };

}