package com.example.fer_medindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmail extends AppCompatActivity {

    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private TextView textViewAuthenticated;
    private  String userOldEmail, userNewEmail, userPwd;
    private Button buttonUpdateEmail;
    private EditText editTextNewEmail,editTextPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        getSupportActionBar().setTitle("Update Email");

        progressBar = findViewById(R.id.progressBar);
        editTextPwd = findViewById(R.id.editText_update_email_verify_password);
        editTextNewEmail = findViewById(R.id.editText_update_email_new);
        textViewAuthenticated = findViewById(R.id.textView_update_email_authenticated);
        buttonUpdateEmail = findViewById(R.id.button_update_email);

        // tắt nút cập nhật email
        buttonUpdateEmail.setEnabled(false);
        editTextNewEmail.setEnabled(false);
        // xác thực firebase
        authProfile = FirebaseAuth.getInstance();
        // lấy người dùng sử dụng biến hồ sơ xác thực
        firebaseUser = authProfile.getCurrentUser();

        // Sử dụng phương thức get email cùng người dùng firebase
        userOldEmail = firebaseUser.getEmail();
        //Liên kết chế độ xem văn bản
        TextView textViewOldEmail = findViewById(R.id.textView_update_email_old);
        // đặt id email cũ or hiện tại lưu vào biến chuỗi
        textViewOldEmail.setText(userOldEmail);

        if(firebaseUser.equals("")){ // Nếu người dùng là null
            Toast.makeText(UpdateEmail.this,"Something went wrong! User's details not available",Toast.LENGTH_LONG).show();
        } else{ // Nếu người dùng không null dùng phương thức xác thực
            reAuthenticate(firebaseUser);
        }
    }
    //Xác thực khi người  dùng thay đổi và cập nhật id email
 //ReAuthenticate / Verify User before updating email
    private void reAuthenticate(FirebaseUser firebaseUser) {
        Button buttonVerifyUser = findViewById(R.id.button_authenticate_user);
        buttonVerifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain password for authentication
                // lấy mật khẩu người dùng từ văn bản chỉnh sửa
                userPwd = editTextPwd.getText().toString();
                // sử dụng mật khẩu của chúng ta xác thực lại người dùng\
                // kiểm tra xem người dùng đã nhập bất kỳ mật khẩu nào hay chưa nếu chưa thì chúng ta hiển thị lỗi
                if(TextUtils.isEmpty(userPwd)){
                    Toast.makeText(UpdateEmail.this,"Password is needed to continue",Toast.LENGTH_LONG).show();
                    editTextPwd.setError("Please enter your password for authentication");
                    editTextPwd.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    // Xác thực người dùng qua id , mật khẩu . Xác thực thông tin bằng email auth nhà cung cấp chấp nhận chứng chỉ
                    // Phương thức này nhận 2 tham số : 1 là email,  2 là mật khẩu
                    AuthCredential credential = EmailAuthProvider.getCredential(userOldEmail,userPwd);
                    // chuyển thông tin đăng nhập vừa mới nhập vào oncompletelistener
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                 //loại bỏ thanh tiến trình có nghĩa là người dùng đã xác thực
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(UpdateEmail.this,"Password has been verified."+
                                    "You can update email now",Toast.LENGTH_LONG).show();

                            // đặt chế độ xem văn bản để hiển thi rằng người dùng đã xác thực
                            textViewAuthenticated.setText("You are authenticated. You can update your email now.");
                            // bật button email và edit new email để xác thực email mới
                            // tắt nút button authenticate và  edit password
                            editTextNewEmail.setEnabled(true);
                            buttonUpdateEmail.setEnabled(true);
                            buttonVerifyUser.setEnabled(false);
                            editTextPwd.setEnabled(false);

                            // Change color of update email button
                            buttonUpdateEmail.setBackgroundTintList(ContextCompat.getColorStateList(UpdateEmail.this,R.color.yellow));
                            buttonUpdateEmail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    userNewEmail = editTextNewEmail.getText().toString();
                                    if(TextUtils.isEmpty(userNewEmail)){
                                        Toast.makeText(UpdateEmail.this,"New Email is required",Toast.LENGTH_LONG).show();
                                        editTextPwd.setError("Please enter your Email");
                                        editTextPwd.requestFocus();
                                    } else  if(!Patterns.EMAIL_ADDRESS.matcher(userNewEmail).matches()){
                                        Toast.makeText(UpdateEmail.this,"Please enter valid Email",Toast.LENGTH_LONG).show();
                                        editTextPwd.setError("Please provide valid Email");
                                        editTextPwd.requestFocus();
                                    }else  if( userOldEmail.matches(userNewEmail)){
                                        Toast.makeText(UpdateEmail.this,"New Email cannot be same as old Email",Toast.LENGTH_LONG).show();
                                        editTextPwd.setError("Please enter new Email");
                                        editTextPwd.requestFocus();
                                    } else {
                                        progressBar.setVisibility(View.VISIBLE);
                                        // gọi một email cập nhật trong phương thúc này
                                        updateEmail(firebaseUser);
                                    }
                                }
                            });
                            } else {
                                try{
                                    throw task.getException();
                                } catch (Exception e){
                                    Toast.makeText(UpdateEmail.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            }
        });

    }

    private void updateEmail(FirebaseUser firebaseUser) {
        // dùng firebase để cập nhật phương thức email
        firebaseUser.updateEmail(userNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    // gửi liên kết xác thực email người dùng
                    firebaseUser.sendEmailVerification();
                    // Email đã cập nhật thành công vui lòng xác thực lại email
                    Toast.makeText(UpdateEmail.this,"Email has been updated . Please verify your new email",Toast.LENGTH_SHORT).show();
                    // bắt đầu hoạt động hồ sơ người dùng và đóng hoạt động email cập nhật
                    Intent intent = new Intent(UpdateEmail.this,UserProfile.class);
                    startActivity(intent);
                    finish();
                }else {
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(UpdateEmail.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //Creating ActionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu items
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Menu Item được chọn
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // lấy id của mục menu được lưu trữ vào int id
        int id = item.getItemId();
        if(id == R.id.menu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        } else if(id == R.id.menu_update_profile) {
            Intent intent = new Intent(UpdateEmail.this,UpdateProfile.class);
            startActivity(intent);
            finish(); // không muốn có nhiều hoạt động trùng lặp đang chạy
        }else if (id == R.id.menu_update_email){
            Intent intent = new Intent(UpdateEmail.this,UpdateEmail.class);
            startActivity(intent);
            finish();
        }/*else if (id == R.id.menu_settings) {
            Toast.makeText(UserProfile.this,"menu_setting",Toast.LENGTH_SHORT).show();
        }*/else if(id == R.id.menu_change_password){
            Intent intent = new Intent(UpdateEmail.this,ChangePassword.class);
            startActivity(intent);
            finish();
        }/*else if(id==R.id.menu_delete_profile){
            Intent intent = new Intent(UserProfile.this,DeleteProfile.class);
            startActivity(intent);
        }*/ else if(id == R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(UpdateEmail.this,"Logged Out",Toast.LENGTH_SHORT).show();
            //quay lại hoạt động chính của Activity
            Intent intent = new Intent(UpdateEmail.this,LoginActivity.class);

            //Xoá ngăn sếp để ngăn người dùng quay lại hoạt động hồ sơ người dùng đã đăng xuất
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();// đóng UserProfile
        }else{ // Nếu ko chọn item nào
            Toast.makeText(UpdateEmail.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}