package com.example.vollup_app_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText name, email, password, balance;
    HashMap<String,String> firebase = new HashMap<>();
    ProgressBar progressBar;
    Button register;
    FirebaseAuth fAuth;
    TextView login;
    private long mLastClickTime = 0;
    String nick, mail, pass, bal, userId;
    double num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //defining element variables:
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        balance = findViewById(R.id.balance);
        register = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        //Hide the progressBar
        progressBar.setVisibility(View.INVISIBLE);

        //add textwatcher for decimal.
        balance.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")){
                    Log.i("regex.","true");
                    int num = s.toString().indexOf(".");
                    Log.i("num.",""+num);
                    if(s.toString().length()==(num+4)){
                        Log.i("ifif.","true");
                        balance.setText(s.subSequence(0, s.length()-1));
                        balance.setSelection(balance.getText().length());
                    }
                }
            }
        });

        //getting firebase authenticator instance.
        fAuth = FirebaseAuth.getInstance();

        //Defining go back to login button action.
        login.setOnClickListener(v -> {
            //Defining double click blocking code
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            //Go back to Login activity.
            startActivity(new Intent(Register.this, Login.class));
            //Finish this activity.
            finish();
        });

        //Defining user register button action.
        register.setOnClickListener(v -> {
            //Defining double click blocking code
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            //Saving info in variables for authentication.

            nick = name.getText().toString().trim();
            mail = email.getText().toString().trim();
            pass = password.getText().toString().trim();
            bal = formatDecimal(balance.getText().toString());

            //Defining register conditions.
            if (name.getText().toString().trim().isEmpty()) {
                name.setError("Fill in your name!");
            } else if (email.getText().toString().trim().isEmpty()) {
                email.setError("Fill in your Email!");
            } else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Fill in your password!");
            } else if (balance.getText().toString().trim().isEmpty()) {
                balance.setError("Fullfuill the balance");
            }
            //If all the form conditions are ok, then we must authenticate the user.
            register.setEnabled (false);
            progressBar.setVisibility(View.VISIBLE);
            //Creating user on Firebase.
            fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    //if user created successfully.
                    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                    assert User != null;
                    //Return User ID.
                    userId = User.getUid();
                    //Save user Info on RealTime Database.
                    DatabaseReference reff = FirebaseDatabase.getInstance("https://vollup-app-android-default-rtdb.firebaseio.com/").getReference().child(userId);
                    firebase.put("name", nick);
                    firebase.put("email", mail);
                    firebase.put("balance",bal);
                    reff.setValue(firebase).addOnCompleteListener(task1 -> {
                        Toast.makeText(getApplicationContext(), "User created successfully!", Toast.LENGTH_LONG).show();
                        //Manage authentication Session
                        SessionManagement sessionManagement = new SessionManagement(Register.this);
                        sessionManagement.saveSession(userId);
                        moveToMainActivity();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failure to create User!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        register.setEnabled (true);
                    });
                }else{
                    //In case creatig user go wrong.
                    Toast.makeText(getApplicationContext(), "Error!" + Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setEnabled (true);
                }
            });
        });
    }

    private String formatDecimal(String number){
        if(!number.contains(".")){
            DecimalFormat format = new DecimalFormat("###,###,###.00");
            return format.format(Long.parseLong(number));
        }else{
            int decimal = number.length()-number.indexOf(".")-1;
            num = Double.parseDouble(number.replace(".", ""));
            num = num/Math.pow(10,decimal);
            DecimalFormat format = new DecimalFormat("###,###,###.00");
            return format.format(num);
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
