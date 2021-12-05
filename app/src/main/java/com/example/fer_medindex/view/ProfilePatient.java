package com.example.fer_medindex.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
            textViewCMND, textViewAddress, textViewStatus, textViewTime, textViewTinhTrangBenh;
    private ProgressBar progressBar;
    private String fullName, email, doB, gender, mobile, cmnd, address, status, createTime, TinhTrangBenh;
    private ImageView imageView;
    private FirebaseAuth authProfile;
    private CircleImageView imageView3;
    private String imgHinh;
    private RelativeLayout relativeLayoutTinhtrang;

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
        textViewTime = findViewById(R.id.textview_show_time);
        progressBar = findViewById(R.id.progressBar);
        relativeLayoutTinhtrang = findViewById(R.id.relativeLayout_tinhtrang);
        authProfile = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String patientId = intent.getStringExtra(PATIENT_ID);

        if (intent.getBooleanExtra(IS_FULL_PROFILE, false)) {
            if(intent.getBooleanExtra(TINH_TRANG_BENH, false)) {
                //hien tinh trang benh o day
                relativeLayoutTinhtrang.setVisibility(View.VISIBLE);
            }
            showUserProfileFromIntent();
        } else {
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Patients");
            referenceProfile.child(patientId).get().addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        ReadWritePatientDetails readWritePatientDetails = task.getResult().getValue(ReadWritePatientDetails.class);
                        if (readWritePatientDetails != null) {
                            readWritePatientDetails.patientId = patientId;
                            if(intent.getBooleanExtra(TINH_TRANG_BENH, false)) {
                                //hien tinh trang benh o day
                                relativeLayoutTinhtrang.setVisibility(View.VISIBLE);
                            }
                            showUserProfileFromFirebase(readWritePatientDetails);
                        }
                    }
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(ProfilePatient.this, "Đã xảy ra lỗi! Thông tin chi tiết của người dùng hiện không có sẵn", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (intent.getBooleanExtra(IS_DOCTOR, false)) {
            // hien cai texxt view de nhap ten benh
            LinearLayout doctorView = findViewById(R.id.doctor_view);
            EditText tinhTrangBenh = findViewById(R.id.editText_chinh_sua);
            TinhTrangBenh = tinhTrangBenh.getText().toString();

            Button saveBtn = findViewById(R.id.save_btn);

            doctorView.setVisibility(View.VISIBLE);
            ReadWritePatientDetails readWritePatientDetails = new ReadWritePatientDetails(fullName, doB, gender, mobile, cmnd, email, address, status, imgHinh, TinhTrangBenh);
            readWritePatientDetails.patientId = patientId;
            saveBtn.setOnClickListener((v) -> {
                if (TextUtils.isEmpty(TinhTrangBenh)) {
                    Toast.makeText(ProfilePatient.this, "Vui lòng nhập thông tin bệnh đầy đủ", Toast.LENGTH_LONG).show();
                    tinhTrangBenh.setError("Bắt buộc nhập thông tin tình trạng bệnh của bệnh nhân");
                    tinhTrangBenh.requestFocus();
                }
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Patients");
                referenceProfile.child(readWritePatientDetails.patientId).child("tinhtrangbenh").setValue(tinhTrangBenh.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //ReadWritePatientDetails readWritePatientDetails = snapshot.getValue(ReadWritePatientDetails.class);
                        textViewTinhTrangBenh = findViewById(R.id.textview_show_tinhtrangbenh);
                        relativeLayoutTinhtrang.setVisibility(View.VISIBLE);
                            readWritePatientDetails.patientId = patientId;
                            fullName = readWritePatientDetails.fullname;
                            email = readWritePatientDetails.email;
                            doB = readWritePatientDetails.ngaysinh;
                            gender = readWritePatientDetails.gioitinh;
                            mobile = readWritePatientDetails.sodienthoai;
                            cmnd = readWritePatientDetails.cmnd;
                            address = readWritePatientDetails.diachi;
                            status = readWritePatientDetails.trangthai;
                            imgHinh = readWritePatientDetails.getImgHinh();
                            createTime = readWritePatientDetails.getCreateTimeString();
                            TinhTrangBenh = tinhTrangBenh.getText().toString();

                            textViewWelcome.setText("Chào mừng " + fullName);
                            textViewFullName.setText(fullName);
                            textViewGmail.setText(email);
                            textViewDoB.setText(doB);
                            textViewGender.setText(gender);
                            textViewMobile.setText(mobile);
                            textViewCMND.setText(cmnd);
                            textViewAddress.setText(address);
                            textViewStatus.setText(status);
                            textViewTime.setText(createTime);
                            textViewTinhTrangBenh.setText(TinhTrangBenh);
                            Glide.with(getApplicationContext())
                                    .load(imgHinh)
                                    .error(R.mipmap.ic_launcher)
                                    .into(imageView3);
                        doctorView.setVisibility(View.GONE);
                        }
                });
            });
        }
    }

    private void showUserProfileFromIntent() {
        progressBar.setVisibility(View.GONE);
        Intent intent = getIntent();
        fullName = intent.getStringExtra(FULLNAME);
        email = intent.getStringExtra(EMAIL);
        doB = intent.getStringExtra(NGAYSINH);
        gender = intent.getStringExtra(GIOITINH);
        mobile = intent.getStringExtra(SODIENTHOAI);
        cmnd = intent.getStringExtra(CMND);
        address = intent.getStringExtra(DIACHI);
        status = intent.getStringExtra(TRANGTHAI);
        imgHinh = intent.getStringExtra(HINHANH);
        createTime = intent.getStringExtra(CREATE_TIME);

        textViewWelcome.setText("Chào mừng " + fullName);
        textViewFullName.setText(fullName);
        textViewGmail.setText(email);
        textViewDoB.setText(doB);
        textViewGender.setText(gender);
        textViewMobile.setText(mobile);
        textViewCMND.setText(cmnd);
        textViewAddress.setText(address);
        textViewStatus.setText(status);
        textViewTime.setText(createTime);
        Glide.with(getApplicationContext())
                .load(imgHinh)
                .error(R.mipmap.ic_launcher)
                .into(imageView3);
    }

    private void showUserProfileFromFirebase(ReadWritePatientDetails readWritePatientDetails) {
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
                    cmnd = readWritePatientDetails.cmnd;
                    address = readWritePatientDetails.diachi;
                    status = readWritePatientDetails.trangthai;
                    imgHinh = readWritePatientDetails.getImgHinh();
                    createTime = readWritePatientDetails.getCreateTimeString();

                    textViewWelcome.setText("Chào mừng " + fullName);
                    textViewFullName.setText(fullName);
                    textViewGmail.setText(email);
                    textViewDoB.setText(doB);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);
                    textViewCMND.setText(cmnd);
                    textViewAddress.setText(address);
                    textViewStatus.setText(status);
                    textViewTime.setText(createTime);
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

    public static String IS_DOCTOR = "IS_DOCTOR";

    public static String IS_FULL_PROFILE = "IS_FULL_PROFILE";

    public static String PATIENT_ID = "PATIENT_ID";

    public static String TINH_TRANG_BENH = "TINH_TRANG_BENH";
    public final static String FULLNAME = "FULLNAME", NGAYSINH = "NGAYSINH", GIOITINH = "GIOITINH", SODIENTHOAI = "SODIENTHOAI", CMND = "CMND", DIACHI = "DIACHI", EMAIL = "EMAIL", TRANGTHAI = "TRANGTHAI", HINHANH = "HINHANH", CREATE_TIME = "CREATE_TIME";
}