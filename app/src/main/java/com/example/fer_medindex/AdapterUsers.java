package com.example.fer_medindex;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.fer_medindex.fragment.ListPatientFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

//public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{
//
//    Context context;
//    List<ReadWritePatientDetails> usersList;
//
//    public AdapterUsers(Context context, List<ReadWritePatientDetails> usersList) {
//        this.context = context;
//        this.usersList = usersList;
//    }
//
//    @NonNull
//    @Override
//    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
//
//        return new MyHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//
//       // String userImage = usersList.get(position).getImage();
//        String userName = usersList.get(position).getFullname();
//        String userEmail = usersList.get(position).getEmail();
//
//        // set data
//        holder.mNameTv.setText(userName);
//        holder.mEmailTv.setText(userEmail);
//        try {
////            Uri uri = firebaseUser.getPhotoUrl();
////            Picasso.with(ListPatientFragment.this).load(uri).into(userImage);
////            Picasso.get().load(userImage)
////                    .placeholder(R.mipmap.ic_launcher_round1)
////                    .into(holder.mAvatarIv);
//        }catch (Exception e){
//
//        }
//        //handle item click
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,""+userEmail,Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return usersList.size();
//    }
//
//    class MyHolder extends RecyclerView.ViewHolder{
//
//        ImageView mAvatarIv;
//        TextView mNameTv , mEmailTv;
//
//
//        public MyHolder(@NonNull View itemView) {
//            super(itemView);
//            mAvatarIv = itemView.findViewById(R.id.avatarIv);
//            mNameTv = itemView.findViewById(R.id.nameTv);
//            mEmailTv = itemView.findViewById(R.id.emailTv);
//        }
//    }
//}
public  class  AdapterUsers extends FirebaseRecyclerAdapter<ReadWritePatientDetails,AdapterUsers.myviewholder> {

    public AdapterUsers(@NonNull FirebaseRecyclerOptions<ReadWritePatientDetails> options) {
        super(options);
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users,parent,false);
        return new myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ReadWritePatientDetails model) {
        holder.textfullname.setText(model.getFullname());
        holder.textAddress.setText(model.getDiachi());
        holder.textmobile.setText(model.getSodienthoai());
            // glide tải hình ảnh và lưu hình ảnh trong bộ đệm
        //Glide.with(holder.img1.getContext()).load(model.getP)

        /*holder.textfullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity =(AppCompatActivity)v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.)
            }
        });*/
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        ImageView img1;
        TextView textfullname , textAddress , textmobile;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.avatarIv);
            textfullname = itemView.findViewById(R.id.nameTv);
            textAddress = itemView.findViewById(R.id.addressTv);
            textmobile=itemView.findViewById(R.id.mobileTv);
        }
    }
}