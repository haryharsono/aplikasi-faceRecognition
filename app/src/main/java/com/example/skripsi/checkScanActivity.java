package com.example.skripsi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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
        System.out.println("cekk :"+matakuliah.getIdZoom());
        System.out.println("cekk :"+matakuliah.getPasswordZoom());
        link.setText(matakuliah.getZoom());
        link.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(link.getText());
                Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        id.setText(matakuliah.getIdZoom());
        id.setOnClickListener(view -> {
            ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(id.getText());
            Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        password.setText(matakuliah.getPasswordZoom());
        password.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(password.getText());
                Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
}