package com.example.fer_medindex.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fer_medindex.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePatient extends AppCompatActivity {
    private TextView textViewWelcome, textViewFullName, textViewGmail, textViewDoB, textViewGender, textViewMobile,
            textViewCMND, textViewAddress, textViewStatus;
    private ProgressBar progressBar;
    private String fullName, email, doB, gender, mobile, CMND, address, status;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    CircleImageView imageView3;
    String imgHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        imageView3 = findViewById(R.id.imageView3);
        textViewWelcome = findViewById(R.id.thong_tin_ben_nhan);
        textViewFullName = findViewById(R.id.textview_show_full_name);
        textViewGmail = findViewById(R.id.textview_show_email);
        textViewDoB = findViewById(R.id.textview_show_dob);
        textViewGender = findViewById(R.id.textview_show_gender);
        textViewMobile = findViewById(R.id.textview_show_mobile);
        textViewCMND = findViewById(R.id.textview_show_passport);
        textViewAddress = findViewById(R.id.textview_show_address);
        textViewStatus = findViewById(R.id.textview_show_status);
        progressBar = findViewById(R.id.progressBar);
        authProfile = FirebaseAuth.getInstance();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Patients");

        String patientId = getIntent().getStringExtra(PATIENT_ID);

        referenceProfile.child(patientId).get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                if (task.getResult() != null) {
                    ReadWritePatientDetails readWritePatientDetails = task.getResult().getValue(ReadWritePatientDetails.class);
                    if (readWritePatientDetails != null) {
                        readWritePatientDetails.patientId = patientId;
                        showUserProfile(readWritePatientDetails);
                    }
                }
            } else {
                Log.e("firebase", "Error getting data", task.getException());
                Toast.makeText(ProfilePatient.this, "Đã xảy ra lỗi! Thông tin chi tiết của người dùng hiện không có sẵn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUserProfile(ReadWritePatientDetails readWritePatientDetails) {
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Patients");

        referenceProfile.child(readWritePatientDetails.patientId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWritePatientDetails readWritePatientDetails = snapshot.getValue(ReadWritePatientDetails.class);
                if (readWritePatientDetails != null) {
                    fullName = readWritePatientDetails.fullname;
                    email = readWritePatientDetails.email;
                    doB = readWritePatientDetails.ngaysinh;
                    gender = readWritePatientDetails.gioitinh;
                    mobile = readWritePatientDetails.sodienthoai;
                    CMND = readWritePatientDetails.cmnd;
                    address = readWritePatientDetails.diachi;
                    status = readWritePatientDetails.trangthai;
                    imgHinh = readWritePatientDetails.getImgHinh();

                    textViewWelcome.setText("Chào mừng " + fullName);
                    textViewFullName.setText(fullName);
                    textViewGmail.setText(email);
                    textViewDoB.setText(doB);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);
                    textViewCMND.setText(CMND);
                    textViewAddress.setText(address);
                    textViewStatus.setText(status);
                    Glide.with(getApplicationContext())
                            .load(imgHinh)
                            .error(R.mipmap.ic_launcher)
                            .into(imageView3);

                } else {
                    Toast.makeText(ProfilePatient.this, "Đã xảy ra lỗi! Thông tin chi tiết của người dùng hiện không có sẵn", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilePatient.this, "Đã xảy ra lỗi! Thông tin chi tiết của người dùng hiện không có sẵn", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void checkEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePatient.this);
        builder.setTitle("Email chưa được xác minh");
        builder.setMessage("Vui lòng xác minh email của bạn ngay bây giờ. Bạn không thể đăng kí mà không xác minh email. ");

        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                //Gửi ứng dụng email trong cửa sổ mới và không phải trong ứng dụng của chúng tôi
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // Tạo AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static String PATIENT_ID = "PATIENT_ID";
}