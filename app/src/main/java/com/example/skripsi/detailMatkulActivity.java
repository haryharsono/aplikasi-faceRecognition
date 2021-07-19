package com.example.skripsi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
    String cekHari="";
    boolean cek=false;
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



//        Date now = new Date();
//        Date waktuSekarang=new Date();
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat convertHari=new SimpleDateFormat("EEEE");
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat dateFormat1=new SimpleDateFormat("kk:mm:ss");
//        String hari=convertHari.format(now);
//        String nilai1=dateFormat1.format(waktuSekarang);
//        LocalDateTime hari= LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("EEEE");
//        String format=hari.format(dateTimeFormatter);
//        LocalDateTime waktu=LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter1=DateTimeFormatter.ofPattern("EEEE");
//        dateTimeFormatter1.format(waktu);

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display i
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Log.d("Tanggal",currentDateandTime);
        if(hari.toString().toLowerCase().equals("monday")){
            cekHari="senin";
        }else if(hari.toString().toLowerCase().equals("tuesday")){
            cekHari="selasa";
        }else if(hari.toString().toLowerCase().equals("wednesday")){
            cekHari="rabu";
        }else if(hari.toString().toLowerCase().equals("thursday")){
            cekHari="kamis";
        }else if(hari.toString().toLowerCase().equals("friday")){
            cekHari="jumat";
        }else if(hari.toString().toLowerCase().equals("saturday")){
            cekHari="sabtu";
        }else if(hari.toString().toLowerCase().equals("sunday")){
            cekHari="minggu";
        }
        Log.d("hari sekarang ",hari.toString().toLowerCase());
 //       String conferReplace=waktu.toString().replace(":","");
//        int result=Integer. parseInt(conferReplace);
        int parseMulai=Integer.parseInt(matkul.getPukulMulai().replace(":",""));
        int parseSelesai=Integer.parseInt(matkul.getPukulSelesai().replace(":",""));
        Log.d("waktu sekarang",currentDateandTime);
        Log.d("waktu mulai",String.valueOf(parseMulai));
        Log.d("waktu selesai",String.valueOf(parseSelesai));
//        if(matkul.getHari().equals(cekHari));{
//            if(parseMulai<=result && result<=parseSelesai){
//                cek=true;
//            }
//        }

        findViewById(R.id.detail_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RecognizeActivity.class));
                if(cek){
                    //simpanKehadiran();
                } else{
                    Toast.makeText(getApplicationContext(),"Kelas Tidak Tersedia", Toast.LENGTH_LONG).show();
                }
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
                    Toast.makeText(getApplicationContext(), "Kelas Tidak Tersedia", Toast.LENGTH_LONG).show();
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