package com.example.fer_medindex.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fer_medindex.R;
import com.example.fer_medindex.adapter.AdapterUsers;
import com.example.fer_medindex.adapter.AdapterUsersPatient;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ListPatientActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    AdapterUsersPatient adapterUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient_activity);

        recyclerView = findViewById(R.id.recyclerView_list_patient);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<ReadWritePatientDetails> options =
                new FirebaseRecyclerOptions.Builder<ReadWritePatientDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patients"), ReadWritePatientDetails.class)
                        .build();

        adapterUsers = new AdapterUsersPatient(options);
      //  adapterUsers.startListening();
        recyclerView.setAdapter(adapterUsers);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterUsers.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapterUsers.stopListening();
    }
}