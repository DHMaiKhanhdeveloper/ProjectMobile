package com.example.fer_medindex.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fer_medindex.adapter.AdapterUsers;
import com.example.fer_medindex.R;
import com.example.fer_medindex.view.ReadWritePatientDetails;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListPatientFragment extends Fragment {

    RecyclerView recyclerView;
    AdapterUsers adapterUsers;
   List<ReadWritePatientDetails> usersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_patient, container, false);

        recyclerView = view.findViewById(R.id.users_recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<ReadWritePatientDetails> options =
                new FirebaseRecyclerOptions.Builder<ReadWritePatientDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patients"), ReadWritePatientDetails.class)
                        .build();
        Log.d("k1", "onCreateView: ");
        usersList = new ArrayList<>();
        
        //getAllUsers();
        adapterUsers = new AdapterUsers(options);
        recyclerView.setAdapter(adapterUsers);

        return view;
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

//    private void getAllUsers() {
//        //get current user
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        //get path of database named "" containing users info
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Patients");
//        //get all data from path
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                usersList.clear();
//                for (DataSnapshot ds: snapshot.getChildren()){
//                    ReadWritePatientDetails readWritePatientDetails = ds.getValue(ReadWritePatientDetails.class);
//
//                    if(!readWritePatientDetails.getPatientId().equals(firebaseUser.getUid())){
//                        usersList.add(readWritePatientDetails);
//                    }
//                    // adapter
//                    adapterUsers = new AdapterUsers(getActivity(),usersList);
//                    // set adapter to recycler view
//                    recyclerView.setAdapter(adapterUsers);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
