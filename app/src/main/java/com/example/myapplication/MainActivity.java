package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    TextInputLayout tilName , tilEmail;
//    EditText etName ,etMobile, etEmail ,etPassword;
//    MaterialButton btClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tilEmail = findViewById(R.id.til_name);
//        tilEmail = findViewById(R.id.til_email);
//        etEmail = findViewById(R.id.et_name);
//        etMobile = findViewById(R.id.et_mobile);
//        etEmail = findViewById(R.id.et_email);
//        etPassword = findViewById(R.id.et_password);
//        btClear = findViewById(R.id.bt_clear);
//
//        etName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().isEmpty() && !s.toString().matches("[a-zA-Z]+")){
//                    tilName.setError("Allow only character");
//                }else{
//                    tilName.setError(null);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        tilEmail.setEndIconOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                etEmail.getText().clear();
//            }
//        });
//        btClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                etEmail.getText().clear();
//                etMobile.getText().clear();
//                etPassword.getText().clear();
//            }
//        });
    }
}