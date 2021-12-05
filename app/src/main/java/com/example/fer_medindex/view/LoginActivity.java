package com.example.fer_medindex.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fer_medindex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPassword;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        editTextLoginEmail = findViewById(R.id.editText_login_doctor_email);
        editTextLoginPassword = findViewById(R.id.editText_login_doctor_password);

        progressBar = findViewById(R.id.progressBar);
        authProfile = FirebaseAuth.getInstance();

        //Reset Password
        TextView textforgotpass = findViewById(R.id.forgot_password);
        textforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "You can reset your password now!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

//        ImageView imageViewShowHidepassword = findViewById(R.id.imageView_show_hide_password);
//        imageViewShowHidepassword.setImageResource(R.drawable.ic_hide_pwd);
//        imageViewShowHidepassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getInstance kiểm tra xem mật khẩu có hiện thị ngay từ đầu hay không
//                if (editTextLoginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
//                    //Nếu mật khẩu hiển thị thì hãy ẩn mật khẩu
//                    editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    // thay đổi icon ẩn
//                    imageViewShowHidepassword.setImageResource(R.drawable.ic_hide_pwd);
//                } else {
//                    //mật khẩu có hiện thị
//                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    // thay đổi icon hiện
//                    imageViewShowHidepassword.setImageResource(R.drawable.ic_show_pwd);
//                }
//            }
//        });
        editTextLoginPassword.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= editTextLoginPassword.getRight() - editTextLoginPassword.getCompoundDrawables()[Right].getBounds().width()) {
                    int selection = editTextLoginPassword.getSelectionEnd();
                    if (passwordVisible) {
                        editTextLoginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible = false;
                    } else {
                        editTextLoginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                        editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible = true;
                    }
                    editTextLoginPassword.setSelection(selection);
                    return true;
                }
            }

            return false;
        });
        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, UserProfile.class);
            startActivity(intent);

        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword = editTextLoginPassword.getText().toString();
                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Your Emai", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginActivity.this, "Please re_enter your email", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Valid Email is required");
                    editTextLoginEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(LoginActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Password is required");
                    editTextLoginEmail.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPassword);
                }
            }
        });
    }

    private void loginUser(String Email, String Password) {
        authProfile.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                    Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                    //lấy phiên bản của người dùng hiện tại
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    //Kiểm tra xem email có được xác minh hay không trước khi người dùng có thể truy cập hồ sơ của họ
                    if (firebaseUser == null) {
                        return;
                    }
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                        // Bắt đầu UserProfileActivity
                        startActivity(new Intent(LoginActivity.this, BackgroundDoctor.class));
                        PatientFormInput.clearForm();
                        finish(); // đóng LoginActivity
                        return;
                        // mở hồ sơ người dùng
                    }
                    authProfile.signOut(); // đăng xuất
                    firebaseUser.sendEmailVerification();
                    showAlertDialog();
                } else {
                    Exception e = task.getException();
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        editTextLoginEmail.setError("User does not exists or is no longer valid.Please register again");
                        editTextLoginEmail.requestFocus();
                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        editTextLoginEmail.setError("Invalid credentials. Kindly , check and re-enter");
                        editTextLoginEmail.requestFocus();
                    } else {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void showAlertDialog() {
        //thiết lập trình tạo cảnh báo
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification. ");

        //Mở ứng dụng email nếu người dùng nhấp / nhấn vào nút tiếp tục
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // xuất hiện trong trình khởi chạy màn hình chính
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                //Gửi ứng dụng email trong cửa sổ mới và không phải trong ứng dụng của chúng tôi
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // Tạo AlertDialog
        AlertDialog alertDialog = builder.create();
        // Hiển thị AlertDialog
        alertDialog.show();

    }

    //Kiểm tra xem người dùng đã đăng nhập chưa.Nếu người dùng đã đăng nhập rồi chuyển đến  trang hồ sơ người dùng
    @Override
    protected void onStart() {
        super.onStart();
        // người dùng không phải null có nghĩa là người dùng đã đăng nhập vào rồi
        if (authProfile.getCurrentUser() != null) {
            Toast.makeText(LoginActivity.this, "Already Logged In!", Toast.LENGTH_SHORT).show();

            // Bắt đầu UserProfileActivity
            startActivity(new Intent(LoginActivity.this, BackgroundDoctor.class));
            finish(); // đóng LoginActivity
        } else {
            Toast.makeText(LoginActivity.this, "You can login now!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onLogin1Click(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

    }
}
