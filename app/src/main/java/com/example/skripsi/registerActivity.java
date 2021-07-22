package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.EndpointData.Endpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {


    EditText nim;
    EditText nama;
    Spinner kelas;
    Spinner semester;
    String[] kelasMhs;
    String getKelas;
    String[] semesterMhs;
    String getSemester;
    EditText alamat;
    EditText noHp;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        getKelas();
        getSemester();
        nim=findViewById(R.id.nim_register);
        nama=findViewById(R.id.nama_register);
        alamat=findViewById(R.id.alamat_register);
        noHp=findViewById(R.id.no_hp_register);
        semester=findViewById(R.id.semester_register);
        kelas=findViewById(R.id.kelas_register);
        password=findViewById(R.id.password_register);

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSemester =semesterMhs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getKelas =kelasMhs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.daftar_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanMahasiswa();
            }
        });
        findViewById(R.id.login_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
            }
        });

    }
    private void simpanMahasiswa() {
        if (nim.getText().length()<7 && nama.getText().toString().isEmpty() && alamat.getText().toString().isEmpty()
                && noHp.getText().length()<12 && password.getText().toString().length()<5){

            Toast.makeText(getApplicationContext(),"Format tidak sesuai",Toast.LENGTH_LONG).show();
        }
        else {
            String url = Endpoint.URL + Endpoint.REGISTER_MAHASISWA;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("error");
                    if (!status) {
                        Toast.makeText(getApplicationContext(), "Berhasil ", Toast.LENGTH_SHORT).show();
                        Intent swap = new Intent(registerActivity.this, TrainActivity.class);
                        startActivity(swap);
                        finish();
                    }
                    else {
                        Toast.makeText(registerActivity.this, "Nim Tersedia", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }, error -> {
                Toast.makeText(registerActivity.this, "Nim Tersedia", Toast.LENGTH_SHORT).show();
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nim", nim.getText().toString());
                    params.put("nama", nama.getText().toString());
                    params.put("alamat", alamat.getText().toString());
                    params.put("no_hp", noHp.getText().toString());
                    params.put("password", password.getText().toString());
                    params.put("kelas", kelas.getSelectedItem().toString());
                    params.put("semester", semester.getSelectedItem().toString());

                    return params;
                }

            };
            Volley.newRequestQueue(getApplicationContext()).add(stringRequest).setShouldCache(false);
        }


    }
    private void getKelas() {
        String url=Endpoint.URL+Endpoint.CEK_KELAS;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("error");
                        if (!status) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            kelasMhs = new String[data.length()];
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                kelasMhs[i] = object.getString("nama_kelas");
                            }
                            kelas.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, kelasMhs));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        finish();
                    }

                }

            }

        }, error -> {

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest).setShouldCache(false);
    }
    private void getSemester() {
        String url=Endpoint.URL+Endpoint.CEK_SEMESTER;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("error");
                        if (!status) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            semesterMhs = new String[data.length()];
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                semesterMhs[i] = object.getString("semester");
                            }
                            semester.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.item_spinner, semesterMhs));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        finish();
                    }

                }

            }

        }, error -> {

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest).setShouldCache(false);
    }
}