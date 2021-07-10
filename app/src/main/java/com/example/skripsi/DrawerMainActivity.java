package com.example.skripsi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.adapter.adapterMatkul;
import com.example.skripsi.model.matkul;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skripsi.databinding.ActivityDrawerMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.OpenCVLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawerMainActivity extends AppCompatActivity {

//    private static String tag="DrawerMainActivity";
//    static {
//        if(OpenCVLoader.initDebug()){
//            Log.d(tag,"sukse");
//
//        }else {
//            Log.d(tag,"gagal");
//        }
//    }

    DBuser dBuser;
    String[] datalist;
    String id;
    TextView name;
    TextView nim;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dBuser = new DBuser( getApplicationContext() );
        datalist = dBuser.getData( FieldUser.NAMA_TABLE );
        id = datalist[0];
        binding = ActivityDrawerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        // get user name and email textViews
         name = headerView.findViewById(R.id.nama_tercantum);
        nim = headerView.findViewById(R.id.nim_tercantum);
        getdata(getApplicationContext());
      DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_drawer_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    void getdata(Context context){
        String url= Endpoint.URL+Endpoint.PROFILE_MAHASISWA_DESIGN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("error");
                if (!status){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        name.setText(jsonObject1.getString("nama"));
                        nim.setText(jsonObject1.getString("nim"));
                       }

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }, error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap=new HashMap<>();
                hashMap.put("id", id);
                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(stringRequest).setShouldCache(false);
    }
}