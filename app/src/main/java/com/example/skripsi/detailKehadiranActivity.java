package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.adapter.adapterKehadiran;
import com.example.skripsi.adapter.adapterRecentKehadiran;
import com.example.skripsi.model.hadir;
import com.example.skripsi.model.matkul;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class detailKehadiranActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterRecentKehadiran adapterRecentKehadiran;
    LinearLayoutManager linearLayoutManager;
    ArrayList<hadir> list;
    DBuser dBuser;
    String[] datalist;
    String id;
    String matkul;
    String detailKehadiranMatkul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_detail_kehadiran);
        dBuser = new DBuser( getApplicationContext() );
        datalist = dBuser.getData( FieldUser.NAMA_TABLE );
        id = datalist[0];
        Intent intent=getIntent();
        detailKehadiranMatkul=intent.getExtras().getString("detail_kehadiran_matkul");
        recyclerView =findViewById(R.id.recycle_daftar_detail_kehadiran);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getdata();
    }
    void getdata(){
        String url= Endpoint.URL+Endpoint.DAFTAR_DETAIL_KEHADIRAN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("error");
                if (!status){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        hadir hadir = new hadir();

                        hadir.setTanggal(jsonObject1.getString("tanggal"));
                        hadir.setWaktu(jsonObject1.getString("waktu"));
                        list.add(hadir);
                        Log.d("tes", hadir.getWaktu());
                        adapterRecentKehadiran = new adapterRecentKehadiran(list, getApplicationContext());
                        recyclerView.setAdapter(adapterRecentKehadiran);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }, error -> {

        }){
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> hashMap=new HashMap<>();
                hashMap.put("id",id);
                hashMap.put("id_jadwal",detailKehadiranMatkul);
                Log.d("tes",id.length()+id+"="+id);

                Log.d("tes","="+detailKehadiranMatkul);
                return hashMap;

            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest).setShouldCache(false);
    }
}