package com.example.skripsi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.model.matkul;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class detailMatkulActivity extends AppCompatActivity {

    TextView dosen;
    TextView matakul;
    TextView tanggal;
    TextView jamMulai;
    TextView jamSelesai;
    DBuser dBuser;
    String []dataList;
    String id;
    TextView hari;
    String cekHari;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_matkul);

        dBuser=new DBuser(getApplicationContext());
        dataList=dBuser.getData(FieldUser.NAMA_TABLE);
        id=dataList[0];
        dosen=findViewById(R.id.detail_dosen);
        matakul=findViewById(R.id.detail_matkul);
  //      tanggal=findViewById(R.id.detail_tanggal);
        jamMulai=findViewById(R.id.detail_jam_mulai);
        jamSelesai=findViewById(R.id.detail_jam_selesai);
        hari=findViewById(R.id.detail_hari);

        Intent intent=getIntent();
        matkul matkul=intent.getParcelableExtra("matkul");
        dosen.setText("Dosen :"+matkul.getDosen());
        hari.setText("Hari :"+matkul.getHari());
        matakul.setText(matkul.getNamaMatkul());
//        tanggal.setText("Tanggal :"+matkul.getTanggal());
        jamSelesai.setText("Pukul Selesai :"+matkul.getPukulSelesai());
        jamMulai.setText("Pukul Mulai :"+matkul.getPukulMulai());



        Date now = new Date();
        Instant current = now.toInstant();
        LocalDateTime ldt = LocalDateTime.ofInstant(current, ZoneId.systemDefault());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormat1=new SimpleDateFormat("hh:mm:ss");
        String nilai=dateFormat.format(now);
        if(nilai.toLowerCase().equals("monday")){
            cekHari="senin";
        }else if(nilai.toLowerCase().equals("tuesday")){
            cekHari="selasa";
        }else if(nilai.toLowerCase().equals("wednesday")){
            cekHari="rabu";
        }else if(nilai.toLowerCase().equals("thursday")){
            cekHari="kamis";
        }else if(nilai.toLowerCase().equals("friday")){
            cekHari="jumat";
        }else if(nilai.toLowerCase().equals("saturday")){
            cekHari="sabtu";
        }else if(nilai.toLowerCase().equals("tuesday")){
            cekHari="minggu";
        }
        try {
            Date date1=dateFormat1.parse(String.valueOf(now));
            int result=Integer.parseInt(nilai);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(hari.getText().toString().equals(cekHari));{
            //Date date=dateFormat1.parse(now)

        }
        findViewById(R.id.detail_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanKehadiran();
            }
        });
        findViewById(R.id.detail_batal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void simpanKehadiran() {


        String url= Endpoint.URL+Endpoint.INSERT_KEHADIRAN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("error");
                if (!status) {
                    Toast.makeText(getApplicationContext(), "Berhasil ", Toast.LENGTH_SHORT).show();
                    Intent swap = new Intent(detailMatkulActivity.this, checkScanActivity.class);
                    startActivity(swap);
                }
                else {
                    Toast.makeText(getApplicationContext(), "waktu Pelajaran Belum Dimulai", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "waktu Pelajaran Belum Dimulai", Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "waktu Pelajaran Belum Dimulai", Toast.LENGTH_LONG).show();
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_mahasiswa", id);
                params.put("id_jadwal", matakul.getText().toString() );

                return params;
            }

        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest).setShouldCache(false);



    }


}