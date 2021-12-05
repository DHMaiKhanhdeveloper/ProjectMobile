package com.example.fer_medindex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fer_medindex.R;
import com.example.fer_medindex.view.ListPatientActivity;
import com.example.fer_medindex.view.ReadWritePatientDetails;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUsersPatient  extends FirebaseRecyclerAdapter<ReadWritePatientDetails,AdapterUsersPatient.myViewHolder> {

    public AdapterUsersPatient(@NonNull FirebaseRecyclerOptions<ReadWritePatientDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ReadWritePatientDetails model) {
        holder.textViewName.setText(model.getFullname());
        holder.textViewTrangThai.setText(model.getTrangthai());
        holder.textViewTinhTrangBenh.setText(model.getTinhtrangbenh());
        Glide.with(holder.img.getContext())
                .load(model.getImgHinh())
                //.placeholder(R.drawable.k1)
                .circleCrop()
                .error(R.mipmap.ic_launcher)
                .into(holder.img);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_patient,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView textViewName , textViewTrangThai, textViewTinhTrangBenh;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            textViewName = itemView.findViewById(R.id.TextViewName);
            textViewTrangThai = itemView.findViewById(R.id.TextViewtrang_thai);
            textViewTinhTrangBenh = itemView.findViewById(R.id.TextViewtinh_trang_benh);


        }
    }

}
