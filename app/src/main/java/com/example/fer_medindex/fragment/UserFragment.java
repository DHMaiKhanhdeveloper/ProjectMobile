package com.example.fer_medindex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fer_medindex.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    private String fullname ,ngaysinh,  gioitinh,  sodienthoai,  cmnd,  diachi,  email,  trangthai, hinhanh;

    public UserFragment(){
    }

    public final static String FULLNAME = "FULLNAME", NGAYSINH = "NGAYSINH", GIOITINH = "GIOITINH", SODIENTHOAI = "SODIENTHOAI", CMND = "CMND", DIACHI = "DIACHI", EMAIL = "EMAIL", TRANGTHAI = "TRANGTHAI", HINHANH = "HINHANH";

    @NonNull
    public static UserFragment create(String fullname, String ngaysinh, String gioitinh, String sodienthoai, String cmnd, String diachi, String email, String trangthai, String hinhanh) {
        Bundle b = new Bundle();
        b.putString(FULLNAME, fullname);
        b.putString(NGAYSINH, ngaysinh);
        b.putString(GIOITINH, gioitinh);
        b.putString(SODIENTHOAI, sodienthoai);
        b.putString(CMND, cmnd);
        b.putString(DIACHI, diachi);
        b.putString(EMAIL, email);
        b.putString(TRANGTHAI, trangthai);
        b.putString(HINHANH, hinhanh);
        UserFragment fragment = new UserFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
            fullname = b.getString(FULLNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView fullnameholder = view.findViewById(R.id.textview_show_full_name);
        TextView gmailholder = view.findViewById(R.id.textview_show_email);
        TextView dobholder = view.findViewById(R.id.textview_show_dob);
        TextView genderholder = view.findViewById(R.id.textview_show_gender);
        TextView mobileholder = view.findViewById(R.id.textview_show_mobile);
        TextView passportholder = view.findViewById(R.id.textview_show_passport);
        TextView addressholder = view.findViewById(R.id.textview_show_address);
        TextView statusholder = view.findViewById(R.id.textview_show_status);
        CircleImageView imageAvatar= view.findViewById(R.id.imageView3);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        fullnameholder.setText(fullname);
        gmailholder.setText(email);
        dobholder.setText(ngaysinh);
        genderholder.setText(gioitinh);
        mobileholder.setText(sodienthoai);
        passportholder.setText(cmnd);
        addressholder.setText(diachi);
        statusholder.setText(trangthai);

        Glide.with(getContext())
                .load(hinhanh)
                .error(R.mipmap.ic_launcher)
                .into(imageAvatar);
        progressBar.setVisibility(View.GONE);
        return view;

    }
//    public void onBackPressed(){
//        AppCompatActivity appCompatActivity =(AppCompatActivity)getContext();
//        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new ListPatientFragment()).addToBackStack(null).commit();
//    }
}