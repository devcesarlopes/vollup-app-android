package com.example.vollup_app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Currency;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Login extends AppCompatActivity {

    TextView register;
    Button loginBotao;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseReference reff = FirebaseDatabase.getInstance("https://vollup-app-android-default-rtdb.firebaseio.com/").getReference().child("hello-world");
        reff.setValue("firebase");

        register = findViewById(R.id.register);
        loginBotao = findViewById(R.id.botao);

        register.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            startActivity(new Intent(Login.this, Register.class));
        });

        loginBotao.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            startActivity(new Intent(Login.this, MainActivity.class));
        });
    }
}