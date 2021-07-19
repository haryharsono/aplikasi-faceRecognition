package com.example.skripsi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.R;
import com.example.skripsi.detailKehadiranActivity;
import com.example.skripsi.model.matkul;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adapterKehadiran extends RecyclerView.Adapter<adapterKehadiran.holderKehadiran> {

    ArrayList<matkul> list;
    Context context;

    public adapterKehadiran(ArrayList<matkul> list,Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public holderKehadiran onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_kehadiran,parent, false);
        return new holderKehadiran(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull holderKehadiran holder, int position) {
        matkul matkul=list.get(position);
        holder.mataKuliah.setText("Nama Mata Kuliah :"+matkul.getNamaMatkul());

        holder.cardView.setOnClickListener(v -> {
            Intent intent=new Intent(context.getApplicationContext(), detailKehadiranActivity.class);
            intent.putExtra("detail_kehadiran_matkul",matkul.getNamaMatkul());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class holderKehadiran extends RecyclerView.ViewHolder {
        ImageView gambar;
        CardView cardView;
        TextView mataKuliah;
        public holderKehadiran(@NonNull @NotNull View itemView) {
            super(itemView);

            gambar=itemView.findViewById(R.id.gambar_daftar_kehadiran);
            mataKuliah=itemView.findViewById(R.id.daftar_kehadiran_matkul);
            cardView=itemView.findViewById(R.id.daftar);
        }
    }
}
