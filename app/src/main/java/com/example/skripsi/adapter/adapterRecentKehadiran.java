package com.example.skripsi.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skripsi.R;
import com.example.skripsi.model.hadir;
import com.example.skripsi.model.matkul;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adapterRecentKehadiran extends RecyclerView.Adapter<adapterRecentKehadiran.holderAdapterRecentKehadiran> {
    ArrayList<hadir> list;
    Context context;

    public adapterRecentKehadiran(ArrayList<hadir> list, Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public holderAdapterRecentKehadiran onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_kehadiran,parent, false);
        return new holderAdapterRecentKehadiran(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull holderAdapterRecentKehadiran holder, int position) {
        hadir cekKehadiran=list.get(position);
        holder.tanggal.setText("Tanggal :"+cekKehadiran.getTanggal());
        holder.waktu.setText("Waktu :"+cekKehadiran.getWaktu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class holderAdapterRecentKehadiran extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView tanggal;
        TextView waktu;

        public holderAdapterRecentKehadiran(@NonNull @NotNull View itemView) {
            super(itemView);
            gambar=itemView.findViewById(R.id.gambar_recent_kehadiran);
            tanggal=itemView.findViewById(R.id.nama_dosen);
            waktu=itemView.findViewById(R.id.nama_matkul);
        }
    }
}
