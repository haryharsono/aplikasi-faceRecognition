package com.example.skripsi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
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
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_matkul);

        dBuser=new DBuser(getApplicationContext());
        dataList=dBuser.getData(FieldUser.NAMA_TABLE);
        id=dataList[0];
        dosen=findViewById(R.id.detail_dosen);
        matakul=findViewById(R.id.detail_matkul);
        tanggal=findViewById(R.id.detail_tanggal);
        jamMulai=findViewById(R.id.detail_jam_mulai);
        jamSelesai=findViewById(R.id.detail_jam_selesai);

        Intent intent=getIntent();
        matkul matkul=intent.getParcelableExtra("matkul");
        dosen.setText("Dosen :"+matkul.getDosen());
        matakul.setText(matkul.getNamaMatkul());
        tanggal.setText("Tanggal :"+matkul.getTanggal());
        jamSelesai.setText("Pukul Selesai :"+matkul.getPukulSelesai());
        jamMulai.setText("Pukul Mulai :"+matkul.getPukulMulai());

        findViewById(R.id.detail_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanKehadiran();
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
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Toast.makeText(detailMatkulActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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