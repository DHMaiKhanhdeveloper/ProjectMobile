package com.example.fer_medindex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fer_medindex.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoctorFragment extends Fragment {
    private TextView textViewWelcome , textViewFullName, textViewDoB, textViewGender , textViewMobile;
    private ProgressBar progressBar;
    private String fullName, email ,doB, gender, mobile;
    private ImageView imageView;
    FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ImageView avatarIv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctor, container, false);

        //init firebase
//        firebaseAuth = FirebaseAuth.getInstance();

    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//            textViewWelcome= textViewWelcome.findViewById(R.id.textview_show_welcome);
//            textViewFullName = textViewFullName.findViewById(R.id.textview_show_full_name);
//            textViewDoB = textViewDoB.findViewById(R.id.textview_show_dob);
//            textViewGender = textViewGender.findViewById(R.id.textview_show_gender);
//            textViewMobile = textViewMobile.findViewById(R.id.textview_show_mobile);
//            progressBar = progressBar.findViewById(R.id.progressBar);
//
//            authProfile = FirebaseAuth.getInstance();
//            FirebaseUser firebaseUser = authProfile.getCurrentUser();
//
//            if(firebaseUser == null) {
//
//            }
//
//
//    }
}
