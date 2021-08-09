package com.example.vollup_app_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email, password;
    ProgressBar progressBar;
    TextView register;
    Button login;
    FirebaseAuth fAuth;
    private long mLastClickTime = 0;
    String mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //defining element variables:
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);

        //Hide the progressBar
        progressBar.setVisibility(View.INVISIBLE);

        //get firebase authentication path.
        fAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> {
            //setting double click blocking code
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            //go to register activity
            startActivity(new Intent(Login.this, Register.class));
        });

        login.setOnClickListener(view -> {
            //setting double click blocking code
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            //Defining login conditions.
            if (email.getText().toString().trim().isEmpty()) {
                email.setError("Fill in your Email!");
                return;
            }else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Fill in your password!");
                return;
            }
            //Show progressbar
            progressBar.setVisibility(View.VISIBLE);

            //adding mail and password to variables.
            mail = email.getText().toString().trim();
            pass = password.getText().toString().trim();

            //block login button
            login.setEnabled (false);
            //Manage authentication Login
            fAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login sucessfully!", Toast.LENGTH_LONG).show();
                    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                    assert User != null;
                    //Manage authentication Session
                    SessionManagement sessionManagement = new SessionManagement(Login.this);
                    sessionManagement.saveSession(User.getUid());
                    moveToMainActivity();
                }else{
                    login.setEnabled (true);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login sucessfully!", Toast.LENGTH_LONG).show();
                }
            });


        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if user is logged in
        //if user is logged in --> move to main activity

        SessionManagement sessionManagement = new SessionManagement(Login.this);
        String userUid = sessionManagement.getSession();
        if(!userUid.equals("-1")){
            //user uid logged in and move to main activity
            moveToMainActivity();
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}