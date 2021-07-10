package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.skripsi.DBuser.DBuser;

import org.opencv.android.OpenCVLoader;

public class splashScreen extends AppCompatActivity {

    static String Tag="Berhasil";
    static {
        if (OpenCVLoader.initDebug()){
            Log.d(Tag,"berhasik");
        }
        else {
            Log.d(Tag,"gagal");
        }
    }

    DBuser dBuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        dBuser=new DBuser(getApplicationContext());
        int waktu_loading = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(dBuser.checkDataUser()) {
                       Intent home = new Intent(splashScreen.this, DrawerMainActivity.class);
                      startActivity(home);
                    finish();
                }else {
                    Intent home = new Intent(splashScreen.this, loginActivity.class);
                    startActivity(home);
                    finish();
                }
            }
        }, waktu_loading);
    }

}