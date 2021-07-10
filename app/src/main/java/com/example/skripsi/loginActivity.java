package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {
    EditText nip;
    EditText password;
    DBuser dbuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);


        dbuser = new DBuser( loginActivity.this );
        nip=findViewById(R.id.nim_login);
        password=findViewById(R.id.password_login);


        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekLogin();
            }
        });
        findViewById(R.id.login_daftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registerActivity.class));
            }
        });
    }
    public void cekLogin(){
        String url= Endpoint.URL+Endpoint.CEK_LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("error");
                if (!status) {
                    dbuser.SqlExecuteQuery( "delete from "+ FieldUser.NAMA_TABLE);
                    JSONArray data = jsonObject.getJSONArray( "data" );
                    for ( int i=0; i<data.length(); i++ ){
                        JSONObject jsonObjectData = data.getJSONObject( i );
                        dbuser.SqlExecuteQuery( "insert into "+FieldUser.NAMA_TABLE+ " values( ' "
                                + jsonObjectData.getString( FieldUser.ID )+ " ') " );

                    }

                    startActivity(new Intent(getApplicationContext(),DrawerMainActivity.class));
                    finish();
                }else{
                    Toast.makeText(loginActivity.this, "NO DATA...! Daftar terlebih dahulu...!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }, error -> {

            Toast.makeText(loginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
        })  {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nim", nip.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest).setShouldCache(false);



    }
}