package com.example.vollup_app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.menu);

        menu.setOnClickListener(view -> {
            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
            sessionManagement.removeSession();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        });
    }
}