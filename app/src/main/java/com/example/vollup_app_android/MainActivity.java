package com.example.vollup_app_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView balance, name;
    ImageView menu;
    String id, nick, bal;
    private long mLastClickTime = 0;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining element variables:
        menu = findViewById(R.id.menu);
        name = findViewById(R.id.name);
        balance = findViewById(R.id.saldo);
        progressBar = findViewById(R.id.progressBar);

        //Get firebaseAuthentication and current user id.
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        if(User != null) {
            id = User.getUid();
        }

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

        reff = FirebaseDatabase.getInstance("https://vollup-app-android-default-rtdb.firebaseio.com/").getReference().child(id);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nick = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                nick = "Olá, "+nick+"\nQual sua próxima operação?";
                bal = Objects.requireNonNull(dataSnapshot.child("balance").getValue()).toString();
                name.setText(nick);
                balance.setText(bal);
                progressBar.setVisibility(View.INVISIBLE);
                reff.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.toString(), Toast.LENGTH_LONG).show();
                balance.setText("-");
                progressBar.setVisibility(View.INVISIBLE);
                reff.removeEventListener(this);
            }
        });
    }
}