package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kehadiran);
        dBuser = new DBuser( getApplicationContext() );
        datalist = dBuser.getData( FieldUser.NAMA_TABLE );
        id = datalist[0];
        Bundle bundle=getIntent().getExtras();
        matkul=bundle.getString("daftar_kehadiran");

        recyclerView =findViewById(R.id.recycle_daftar_detail_kehadiran);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        getdata(getApplicationContext());
    }
    void getdata(Context context){
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

                        hadir.setTanggal(jsonObject1.getString("id"));
                        hadir.setWaktu(jsonObject1.getString("id_jadwal"));

                        list.add(hadir);
                        adapterRecentKehadiran = new adapterRecentKehadiran(list, context);
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
                hashMap.put("id", id);
                hashMap.put("id_jadwal", matkul);
                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(stringRequest).setShouldCache(false);
    }
}