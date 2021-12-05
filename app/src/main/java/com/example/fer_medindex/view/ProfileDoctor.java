package com.example.fer_medindex.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fer_medindex.R;
import com.example.fer_medindex.fragment.ListPatientFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDoctor extends AppCompatActivity {
    private CircleImageView imageAvatar;
    private TextView fullnameholder;
    private TextView gmailholder ;
    private TextView dobholder ;
    private TextView genderholder;
    private TextView mobileholder ;
    private TextView passportholder;
    private TextView addressholder ;
    private TextView statusholder ;
    private ProgressBar progressBar;
    private String fullname ,ngaysinh,  gioitinh,  sodienthoai,  cmnd,  diachi,  email,  trangthai, hinhanh;

    public  ProfileDoctor(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);
        Intent intent = getIntent();



        TextView fullnameholder = findViewById(R.id.textview_show_full_name);
        TextView gmailholder = findViewById(R.id.textview_show_email);
        TextView dobholder = findViewById(R.id.textview_show_dob);
        TextView genderholder = findViewById(R.id.textview_show_gender);
        TextView mobileholder = findViewById(R.id.textview_show_mobile);
        TextView passportholder = findViewById(R.id.textview_show_passport);
        TextView addressholder = findViewById(R.id.textview_show_address);
        TextView statusholder = findViewById(R.id.textview_show_status);
        CircleImageView imageAvatar= findViewById(R.id.imageView3);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        fullnameholder.setText(fullname);
        gmailholder.setText(email);
        dobholder.setText(ngaysinh);
        genderholder.setText(gioitinh);
        mobileholder.setText(sodienthoai);
        passportholder.setText(cmnd);
        addressholder.setText(diachi);
        statusholder.setText(trangthai);


        Glide.with(getApplicationContext())
                .load(hinhanh)
                .error(R.mipmap.ic_launcher)
                .into(imageAvatar);
        progressBar.setVisibility(View.GONE);


    }


}