package com.example.zakatpaymentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnZakat = findViewById(R.id.btnZakat);
        Button btnAbout = findViewById(R.id.btnAbout);

        btnZakat.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ZakatActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}
