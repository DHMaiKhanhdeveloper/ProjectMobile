package com.example.fer_medindex.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fer_medindex.adapter.AdapterUsers;
import com.example.fer_medindex.R;
import com.example.fer_medindex.view.AddActivity;
import com.example.fer_medindex.view.ProfilePatient;
import com.example.fer_medindex.view.ReadWritePatientDetails;
import com.example.fer_medindex.view.Register_Patient;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.List;

public class ListPatientFragment extends Fragment {

    private EditText inputSearch;
    private RecyclerView recyclerView;
    private AdapterUsers adapterUsers;
    private List<ReadWritePatientDetails> usersList;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_patient, container, false);

        recyclerView = view.findViewById(R.id.users_recyclerView);
        floatingActionButton = view.findViewById(R.id.FloatingActionButton);

        //inputSearch = view.findViewById(R.id.inputSearch);

        FirebaseRecyclerOptions<ReadWritePatientDetails> options =
                new FirebaseRecyclerOptions.Builder<ReadWritePatientDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patients"), ReadWritePatientDetails.class)
                        .build();
        adapterUsers = new AdapterUsers(options, this);
        adapterUsers.startListening();
        recyclerView.setAdapter(adapterUsers);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usersList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivity(intent);
            }
        });

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);// show menu options in fragment
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        //SearchView
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(item);
        //search listener
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               //called when user press seach button form key board
               //if search query is not empty then search (nếu thanh tìm kiếm có chữ nhấn search)
             //  if(!TextUtils.isEmpty(query.trim())) { // trim bỏ những khoảng cách ở đầu và cuối
                   // search text contains text , search ít
                   SearchUsers(query);
                 //  }
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               //called whenever user press any single letter

            SearchUsers(newText);
               return false;
           }
       });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void SearchUsers(String query) {
        FirebaseRecyclerOptions<ReadWritePatientDetails> options =
                new FirebaseRecyclerOptions.Builder<ReadWritePatientDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patients").orderByChild("fullname").startAt(query).endAt(query+"~"), ReadWritePatientDetails.class)
                        .build();
        adapterUsers = new AdapterUsers(options, this);
        adapterUsers.startListening();
        recyclerView.setAdapter(adapterUsers);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.logout){
//            firebaseAuth.signOut();
//            checkUserStatus();
//        }
////        if(id== R.id.search){
////
////        }
//        return super.onOptionsItemSelected(item);
//    }

    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

        }else{
//            startActivity(new Intent(getActivity(), Register_Patient.class));
//            getActivity().finish();
        }
    }

    public void onPlaceClick(int position) {
        Intent intent = new Intent( ListPatientFragment.this.getActivity ()
                ,UserFragment.class );
        intent.putExtra("Place Item", position);
        startActivity ( intent );
    }
    private void getAllUsers() {

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
    }
}
