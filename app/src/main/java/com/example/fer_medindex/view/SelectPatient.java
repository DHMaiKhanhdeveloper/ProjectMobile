package com.example.fer_medindex.view;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.fer_medindex.R;

public class SelectPatient extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);
        ImageView imageViewDoctor = findViewById(R.id.Register_Background_Patient);
        imageViewDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(SelectPatient.this, Register_Patient.class);
            startActivity(intent);
        });
        ImageView imageViewPatient = findViewById(R.id.xem_lai_form);
        imageViewPatient.setOnClickListener(v -> {
            ReadWritePatientDetails patientDetails = PatientFormInput.getForm();
            if (patientDetails == null) {
                new AlertDialog.Builder(SelectPatient.this)
                        .setMessage("Bạn chưa điền form nào, bạn có muốn điền một form không?")
                        .setPositiveButton("OK", (dialog, which) -> {
                            Intent intent = new Intent(SelectPatient.this, Register_Patient.class);
                            startActivity(intent);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {

                        })
                        .create()
                        .show();
            } else {
                Intent intent = new Intent(SelectPatient.this, ProfilePatient.class);
                intent.putExtra(ProfilePatient.IS_FULL_PROFILE , true);
                intent.putExtra(ProfilePatient.PATIENT_ID, patientDetails.patientId);
                intent.putExtra(ProfilePatient.FULLNAME, patientDetails.fullname);
                intent.putExtra(ProfilePatient.EMAIL,patientDetails.email);
                intent.putExtra(ProfilePatient.NGAYSINH,patientDetails.ngaysinh);
                intent.putExtra(ProfilePatient.GIOITINH,patientDetails.gioitinh);
                intent.putExtra(ProfilePatient.SODIENTHOAI,patientDetails.sodienthoai);
                intent.putExtra(ProfilePatient.CMND,patientDetails.cmnd);
                intent.putExtra(ProfilePatient.DIACHI,patientDetails.diachi);
                intent.putExtra(ProfilePatient.TRANGTHAI,patientDetails.trangthai);
                intent.putExtra(ProfilePatient.CREATE_TIME,patientDetails.getCreateTimeString());
                intent.putExtra(ProfilePatient.HINHANH,patientDetails.imgHinh);
                //put profile patient to intent
                startActivity(intent);
            }
        });
        ImageView imageViewDanh_Sach = findViewById(R.id.danh_sach_benh_nhan);
        imageViewDanh_Sach.setOnClickListener(v -> {
            Intent intent = new Intent(SelectPatient.this,ListPatientActivity.class);
            startActivity(intent);
        });
    }
}