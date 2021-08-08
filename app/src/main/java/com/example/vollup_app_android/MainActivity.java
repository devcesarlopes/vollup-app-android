package com.example.vollup_app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView menu;
    String id;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.menu);
        progressBar = findViewById(R.id.progressBar);

        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        if(User != null) {
            id = User.getUid();
        }

        System.out.println(id);

        menu.setOnClickListener(view -> {
            //Defining double click blocking code
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            //Leave session
            SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
            sessionManagement.removeSession();
            //Move to Login Activity
            startActivity(new Intent(MainActivity.this, Login.class));
            //Finish Main Activity
            finish();
        });
    }
}