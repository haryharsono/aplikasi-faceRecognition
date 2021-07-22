package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.skripsi.model.matkul;

public class checkScanActivity extends AppCompatActivity {

    TextView link;
    TextView id;
    TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_scan);

        link=findViewById(R.id.link_kelas_check);
        id=findViewById(R.id.id_kelas_check);
        password=findViewById(R.id.password_kelas_check);



        Intent intent=getIntent();
        matkul matakuliah=intent.getParcelableExtra("zoom");

        System.out.println("cekk :"+matakuliah.getZoom());
        link.setText(matakuliah.getZoom());
        id.setText(matakuliah.getIdZoom());
        password.setText(matakuliah.getPasswordZoom());
    }
}