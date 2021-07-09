package com.example.skripsi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.R;
import com.example.skripsi.RecognizeActivity;
import com.example.skripsi.detailKehadiranActivity;
import com.example.skripsi.detailMatkulActivity;
import com.example.skripsi.model.matkul;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapterMatkul extends RecyclerView.Adapter<adapterMatkul.holderMatkul> {
    ArrayList<matkul> list;
    Context context;
    public adapterMatkul(ArrayList<matkul> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public holderMatkul onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_matkul,parent, false);
        return new holderMatkul(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull holderMatkul holder, int position) {

        matkul matkul = list.get(position);
        holder.namaDosen.setText("Nama : "+matkul.getDosen());
        holder.namaMatkul.setText("Mata Kuliah : "+matkul.getNamaMatkul());
        holder.tanggal.setText("Tanggal : "+matkul.getTanggal());
        holder.pukulMulai.setText("Pukul : "+matkul.getPukulMulai());
        holder.pukulSelesai.setText("Pukul : "+matkul.getPukulSelesai());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(context.getApplicationContext(), detailMatkulActivity.class);
             intent.putExtra("matkul",matkul);
             context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class holderMatkul extends RecyclerView.ViewHolder {
        ImageView gambar;
        TextView namaDosen;
        TextView namaMatkul;
        TextView tanggal;
        TextView pukulMulai;
        TextView pukulSelesai;
        CardView cardView;



        public holderMatkul(@NonNull @NotNull View itemView) {
            super(itemView);

            gambar=itemView.findViewById(R.id.gambar);
            namaDosen=itemView.findViewById(R.id.nama_dosen);
            namaMatkul=itemView.findViewById(R.id.nama_matkul);
            tanggal=itemView.findViewById(R.id.tanggal);
            pukulMulai=itemView.findViewById(R.id.waktu_mulai);
            pukulSelesai=itemView.findViewById(R.id.waktu_selesai);
            cardView=itemView.findViewById(R.id.card_matkul);

        }
    }
    public void filterList(ArrayList<matkul> matkulArrayList){
        list=matkulArrayList;
        notifyDataSetChanged();
    }

}
