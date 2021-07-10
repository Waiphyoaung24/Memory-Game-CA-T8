package com.example.memorygameca.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memorygameca.R;
import com.google.android.material.button.MaterialButton;

public class Main_menuActivity extends AppCompatActivity {

    MaterialButton btnPlay;
    MaterialButton btnHost;
    MaterialButton btnJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        initComponents();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_menuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initComponents(){
        btnPlay = findViewById(R.id.btn_play);
        btnHost = findViewById(R.id.btn_host);
        btnJoin = findViewById(R.id.btn_join);
    }
}