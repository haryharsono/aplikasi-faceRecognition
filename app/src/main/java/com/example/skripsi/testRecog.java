package com.example.skripsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class testRecog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recog);

        Button train = (Button)findViewById(R.id.btn_train);
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent swap = new Intent(testRecog.this, TrainActivity.class);
                startActivity(swap);
            }
        });
        Button recognize = (Button)findViewById(R.id.btn_recognize);
        recognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent swap = new Intent(testRecog.this, RecognizeActivity.class);
                startActivity(swap);
            }
        });
    }
    }