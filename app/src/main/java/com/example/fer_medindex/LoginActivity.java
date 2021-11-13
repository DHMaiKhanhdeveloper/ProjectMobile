package com.example.fer_medindex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fer_medindex.fragment.DoctorFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail , editTextLoginPassword;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPassword = findViewById(R.id.editText_login_password);

        progressBar = findViewById(R.id.progressBar);
        authProfile = FirebaseAuth.getInstance();

        ImageView imageViewShowHidepassword = findViewById(R.id.imageView_show_hide_password);
        imageViewShowHidepassword.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getInstance kiểm tra xem mật khẩu có hiện thị ngay từ đầu hay không
                if(editTextLoginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //Nếu mật khẩu hiển thị thì hãy ẩn mật khẩu
                    editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // thay đổi icon ẩn
                    imageViewShowHidepassword.setImageResource(R.drawable.ic_hide_pwd);
                }else{
                    //mật khẩu có hiện thị
                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // thay đổi icon hiện
                    imageViewShowHidepassword.setImageResource(R.drawable.ic_show_pwd);
                }

            }
        });

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,BackgroundDoctor.class);
            startActivity(intent);

        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextLoginEmail.getText().toString();
                String textPassword = editTextLoginPassword.getText().toString();
                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(LoginActivity.this, "Please Enter Your Emai", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(LoginActivity.this,"Please re_enter your email",Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Valid Email is required");
                    editTextLoginEmail.requestFocus();
                }else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(LoginActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Password is required");
                    editTextLoginEmail.requestFocus();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPassword);
                }

            }
        });
    }

    private void loginUser(String Email, String Password) {
        authProfile.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                    //lấy phiên bản của người dùng hiện tại
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
                    //Kiểm tra xem email có được xác minh hay không trước khi người dùng có thể truy cập hồ sơ của họ
                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                        // mở hồ sơ người dùng
                    }else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut(); // đăng xuất
                        showAlertDialog();
                    }
                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextLoginEmail.setError("User does not exists or is no longer valid.Please register again");
                        editTextLoginEmail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextLoginEmail.setError("Invalid credentials. Kindly , check and re-enter");
                        editTextLoginEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG , e.getMessage());
                        Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
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
        if(authProfile.getCurrentUser() !=null){
            Toast.makeText(LoginActivity.this,"Already Logged In!",Toast.LENGTH_SHORT).show();

            // Bắt đầu UserProfileActivity
            startActivity(new Intent(LoginActivity.this, BackgroundDoctor.class));
            finish(); // đóng LoginActivity
        } else {
            Toast.makeText(LoginActivity.this,"You can login now!",Toast.LENGTH_SHORT).show();
        }
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }
}
