package com.example.fer_medindex;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Patient extends AppCompatActivity {
    private EditText editTextRegisterFullName, editTextRegisterEmail,
            editTextRegisterDoB, editTextRegisterMobile, editTextRegiterCMND, editRegisterAddress;
    private ProgressBar progressBar;
    private RadioButton radioButtonRegisterGenderSelected;
    private RadioGroup radioGroupRegisterGender;
    private CheckBox checkBoxHo, checkBoxSot, checkBoxKhotho, checkboxdaunguoi, checkboxsuckhoetot, checkboxcamket;
    private DatePickerDialog picker;
    private FirebaseDatabase firebaseDatabase;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        editTextRegisterFullName = findViewById(R.id.editText_register_patient_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_patient_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_patient_dob);
        editTextRegisterMobile = findViewById(R.id.editText_register_patient_mobile);
        editTextRegiterCMND = findViewById(R.id.editText_register_patient_passport);
        editRegisterAddress = findViewById(R.id.editText_register_patient_address);

        checkBoxHo = findViewById(R.id.checkbox_ho);
        checkBoxSot = findViewById(R.id.checkbox_sot);
        checkBoxKhotho = findViewById(R.id.checkbox_kho_tho);
        checkboxdaunguoi = findViewById(R.id.checkbox_dau_nguoi);
        checkboxsuckhoetot = findViewById(R.id.checkbox_suc_khoe_tot);
        progressBar = findViewById(R.id.progressBar);

        checkboxcamket = findViewById(R.id.checkbox_register_patient_CheckAgainst);

        radioGroupRegisterGender = findViewById(R.id.radio_group_register_patient_gender);
        radioGroupRegisterGender.clearCheck();


        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(Register_Patient.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextRegisterDoB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day); // 3 tham số xác định
                picker.show();

            }
        });

        Button buttonRegisterPatient = findViewById(R.id.button_register_patient);
        buttonRegisterPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textCMND = editTextRegiterCMND.getText().toString();
                String textAddress = editRegisterAddress.getText().toString();
                String textHo = checkBoxHo.getText().toString();
                String textSot = checkBoxSot.getText().toString();
                String textKhotho = checkBoxKhotho.getText().toString();
                String textDaunguoi = checkboxdaunguoi.getText().toString();
                String textSuckhoetot = checkboxsuckhoetot.getText().toString();
                String textcamket = checkboxcamket.getText().toString();
                String textGender;


                String mobileRegex = "[0][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex); // xác định mẫu di động
                mobileMatcher = mobilePattern.matcher(textMobile);


                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập đầy đủ họ và tên của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Bắt buộc nhập họ và tên");
                    editTextRegisterFullName.requestFocus();// yeu cau nhap lai
                } else if (TextUtils.isEmpty((textEmail))) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập email của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc nhập email");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) { // khác true
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập lại email của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError(" email không hợp lệ");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDoB)) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập ngày sinh của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterDoB.setError("Bắt buộc nhập ngày sinh");
                    editTextRegisterDoB.requestFocus();
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Register_Patient.this, "Vui lòng chọn giới tính của bạn", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Bắt buộc phải chọn giới tính");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập số điện thoại của bạn ", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Bắt buộc nhập số điện thoại");
                    editTextRegisterMobile.requestFocus();
                } else if (textMobile.length() != 10) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập số điện thoại của bạn ", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Điện thoại di động phải có 10 chữ số");
                    editTextRegisterMobile.requestFocus();
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập số điện thoại của bạn ", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Điện thoại di động không hợp lệ");
                    editTextRegisterMobile.requestFocus();
                } else if (TextUtils.isEmpty((textCMND))) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập CMND của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc nhập CMND");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty((textAddress))) {
                    Toast.makeText(Register_Patient.this, "Vui lòng nhập địa chỉ của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc nhập địa chỉ");
                    editTextRegisterEmail.requestFocus();
                } else if (!checkBoxHo.isChecked() && !checkBoxSot.isChecked() && !checkboxdaunguoi.isChecked() && !checkBoxKhotho.isChecked() && !checkboxdaunguoi.isChecked() && !checkboxsuckhoetot.isChecked()) {
                    Toast.makeText(Register_Patient.this, "Vui lòng chọn tình trạng hiện tại của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc chọn tình trạng hiện tại");
                    editTextRegisterEmail.requestFocus();
                } else if (!checkboxcamket.isChecked()) {
                    Toast.makeText(Register_Patient.this, "Vui lòng chọn cam kết những thông tin khai là đúng sự thật", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc chọn cam kết");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textSot) && TextUtils.isEmpty(textHo) && TextUtils.isEmpty(textKhotho) && TextUtils.isEmpty(textDaunguoi) && TextUtils.isEmpty(textSuckhoetot)) {
                    Toast.makeText(Register_Patient.this, "Vui lòng chọn tình trạng hiện tại của bạn", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc chọn tình trạng hiện tại");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textcamket)) {
                    Toast.makeText(Register_Patient.this, "Vui lòng chọn cam kết những thông tin khai là đúng sự thật", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Bắt buộc chọn cam kết");
                    editTextRegisterEmail.requestFocus();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);

                    ReadWritePatientDetails writerPatientDetails = new ReadWritePatientDetails(textFullName,textDoB, textGender, textMobile, textCMND, textEmail, textAddress, Status());

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Patients");

                    referenceProfile.child(writerPatientDetails.patientId).setValue(writerPatientDetails).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            PatientFormInput.createFrom(writerPatientDetails);
                            Toast.makeText(Register_Patient.this, "User registered successfully, Please vertify your email", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(Register_Patient.this, ProfilePatient.class);
                            intent.putExtra(ProfilePatient.PATIENT_ID, writerPatientDetails.patientId);
                            // Ngan nguoi dung dang ki thanh cong khong quay lai dang ki lai lan nua , nguoi dung dang ki thanh cong se chuyen den trang ho so
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                            finish(); // dong hoat dong Register
                        } else {
                            Toast.makeText(Register_Patient.this, "User registered failed, Please try again", Toast.LENGTH_LONG).show();
                        }
                        // ẩn progressBar khi người dùng đăng kí thành công hoặc thất bại
                        progressBar.setVisibility(View.GONE);
                    });

                }
            }
        });

    }

    private String Status() {
        String status = "";
        if (checkBoxHo.isChecked()) {
            status += "Ho";
        }
        if (checkBoxSot.isChecked()) {
            status += " Sốt";
        }
        if (checkboxdaunguoi.isChecked()) {
            status += " Khó thở";
        }
        if (checkBoxKhotho.isChecked()) {
            status += " Đau người , mệt mỏi";
        }
        if (checkboxsuckhoetot.isChecked()) {
            status += " Sức khoẻ tốt";
        }
        return status;

    }


}