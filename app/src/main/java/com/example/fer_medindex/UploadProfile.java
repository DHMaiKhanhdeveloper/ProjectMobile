package com.example.fer_medindex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.appsearch.StorageInfo;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadProfile extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageViewUploadPic;
    private FirebaseAuth authProfile;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile);

        getSupportActionBar().setTitle("Upload Profile Picture");

        Button buttonUploadPicChoose = findViewById(R.id.upload_pic_choose_button);
        Button buttonUploadPic = findViewById(R.id.upload_pic_button);
        progressBar = findViewById(R.id.progressBar);
        imageViewUploadPic = findViewById(R.id.imageView_upload_profile_user);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference("DisplayPics");

        Uri uri = firebaseUser.getPhotoUrl();
        //Set User's current DP in ImageView ( if uploaded already). We will Picasso since imageViewer setImage
        //Regular URIs.
        //Người dùng tải ảnh lên rồi
        Picasso.with(UploadProfile.this).load(uri).into(imageViewUploadPic);

        // Chọn hình ảnh để tải lên
        buttonUploadPicChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        //Cập nhật hình ảnh lên profile
        buttonUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                uploadPic();
            }
        });

    }

    private void uploadPic() {
        if(uriImage != null){
        // Save the image with uid of the currently logged user
            // tham chiếu lưu trữ không gian lưu trữ , lựa chọn hiển thị vị trí lưu hình ảnh dưới dạng con và tên là uid người dùng
            // cộng thêm phần mở rộng hình ảnh
            StorageReference fileReference = storageReference.child(authProfile.getCurrentUser().getUid() + "." + getFileExtension(uriImage));

            //Upload image to Storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // đặt ảnh hồ sơ trong chế độ xem hình ảnh và tham chiếu cho hình ảnh này được lưu trữ trong tệp tham chiếu
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override // download ảnh về thành công
                        public void onSuccess(Uri uri) {
                            Uri downloaduri = uri;
                            firebaseUser = authProfile.getCurrentUser();

                            //Finally set the display image of user after upload
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloaduri).build();
                            firebaseUser.updateProfile(profileUpdates);
                        }
                    });
                    //Quá trình upload diễn ra thành công
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(UploadProfile.this,"Upload Successful!",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UploadProfile.this,UserProfile.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        //Giúp cho một ứng dụng quản lý quyền truy cập đến dữ liệu được lưu bởi ứng dụng đó
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        // /* là chọn bất cứ hình ảnh loại gì cũng được
        intent.setType("image/*");
        // lấy nội dung trên Internet ,bên trong android
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // PICK_IMAGE_REQUEST= 1 yêu cầu chọn ảnh là đúng
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // kiểm tra mã yêu cầu có bằng yêu cầu hình ảnh ko ? , dữ liệu ko rỗng
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            // đặt hình ảnh người dùng chọn vào trong nền hình ảnh android
            // người dùng chọn hình ảnh trong dữ liệu điện thoại
            uriImage = data.getData();
            imageViewUploadPic.setImageURI(uriImage);

        }
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
            Intent intent = new Intent(UploadProfile.this,UpdateProfile.class);
            startActivity(intent);
            finish(); // không muốn có nhiều hoạt động trùng lặp đang chạy
        }else if (id == R.id.menu_update_email){
            Intent intent = new Intent(UploadProfile.this,UpdateEmail.class);
            startActivity(intent);
            finish();
        }/*else if (id == R.id.menu_settings) {
            Toast.makeText(UserProfile.this,"menu_setting",Toast.LENGTH_SHORT).show();
        }*/else if(id == R.id.menu_change_password){
            Intent intent = new Intent(UploadProfile.this,ChangePassword.class);
            startActivity(intent);
            finish();
        }/*else if(id==R.id.menu_delete_profile){
            Intent intent = new Intent(UserProfile.this,DeleteProfile.class);
            startActivity(intent);
        }*/ else if(id == R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(UploadProfile.this,"Logged Out",Toast.LENGTH_SHORT).show();
            //quay lại hoạt động chính của Activity
            Intent intent = new Intent(UploadProfile.this,LoginActivity.class);

            //Xoá ngăn sếp để ngăn người dùng quay lại hoạt động hồ sơ người dùng đã đăng xuất
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();// đóng UserProfile
        }else{ // Nếu ko chọn item nào
            Toast.makeText(UploadProfile.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}