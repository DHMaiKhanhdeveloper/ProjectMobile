package com.example.fer_medindex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fer_medindex.R;
import com.example.fer_medindex.view.ReadWriteUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorFragment extends Fragment {

    private TextView textViewWelcome , textViewFullName, textViewDoB, textViewGender , textViewMobile,textViewGmail;
    private ProgressBar progressBar;
    private String fullName, email ,doB, gender, mobile;
    private ImageView imageView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ImageView avatarIv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    CircleImageView imageAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Registered Users");

        imageAvatar = view.findViewById(R.id.imageView3);
        //init view
        avatarIv = view.findViewById(R.id.imageView3);
        textViewWelcome = view.findViewById(R.id.thong_tin_bac_si);
        textViewFullName = view.findViewById(R.id.textview_show_full_name);
        textViewGmail = view.findViewById(R.id.textview_show_email);
        textViewGender = view.findViewById(R.id.textview_show_gender);
        textViewDoB = view.findViewById(R.id.textview_show_dob);
        textViewGender = view.findViewById(R.id.textview_show_gender);
        textViewMobile = view.findViewById(R.id.textview_show_mobile);
        progressBar = view.findViewById(R.id.progressBar);

        Query query = databaseReference.orderByKey().equalTo(user.getUid()).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ReadWriteUserDetails result = ds.getValue(ReadWriteUserDetails.class);
                    if(result == null) {
                        return;
                    }
                    textViewGmail.setText(result.getEmail());
                    textViewFullName.setText(result.getFullName());
                    textViewMobile.setText(result.getMobile());
                    textViewGender.setText(result.getGender());
                    textViewDoB.setText(result.getDoB());
                    Glide.with(getContext())
                            .load(result.getImgAvatar())
                            .error(R.mipmap.ic_launcher)
                            .into(imageAvatar);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        progressBar.setVisibility(View.GONE);

        return view;

    }
}
