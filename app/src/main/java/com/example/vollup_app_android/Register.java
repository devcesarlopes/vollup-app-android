package com.example.vollup_app_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText name, email, password, balance;
    ProgressBar progressBar;
    Button register;
    FirebaseAuth fAuth;
    TextView login;
    private long mLastClickTime = 0;
    String nick, mail, pass, bal;

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

        //TextWatcher to keep money decimal places in balance.
        balance.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$"))
                {
                    String userInput= ""+s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length()-2, '.');
                    cashAmountBuilder.insert(0, '$');

                    balance.setText(cashAmountBuilder.toString());
                    // keeps the cursor always to the right
                    Selection.setSelection(balance.getText(), cashAmountBuilder.toString().length());
                }
            }
        });

        //getting firebase authenticator instance.
        //fAuth = FirebaseAuth.getInstance();

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

            //Savind info in variables for authentication.

            nick = name.getText().toString().trim();
            mail = email.getText().toString().trim();
            pass = password.getText().toString().trim();
            bal = balance.getText().toString().trim();

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
            /*
            register.setEnabled (false);
            progressBar.setVisibility(View.VISIBLE);
            //Criando usuário no Firebase.
            fAuth.createUserWithEmailAndPassword(email,rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Se criado com sucesso
                        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
                        assert User != null;
                        //Retornar ID de usuário
                        UserId = User.getUid();
                        //Salvar informações de usuário no RealTime Database.
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("users").child(UserId);
                        reff.child("nome").setValue(nome);
                        reff.child("email").setValue(email);
                        reff.child("phone").setValue(phone);
                        reff.child("cargo").setValue(cargo);
                        reff.child("usuario").setValue ("Usuario");
                        if(ImageCheck){
                            //SavePATHOnSharedPreferences(getApplicationContext(), PATH);
                        }
                        Toast.makeText(activity_register.this, "Usuário Criado com Sucesso!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        mRegisterBtn.setEnabled (true);
                        finish();
                    }else{
                        //Caso ocorra erro ao criar usuário
                        Toast.makeText(activity_register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        mRegisterBtn.setEnabled (true);
                    }
                }
            });
        });
    }*/
        });
    }
}