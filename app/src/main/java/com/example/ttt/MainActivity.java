package com.example.ttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextView create_account;
    private FirebaseAuth mAuth;
    EditText userEmail, userPass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        login  = (Button) findViewById(R.id.btn_login);
        create_account  = (TextView) findViewById(R.id.txt_create_account);
        userEmail = (EditText)findViewById(R.id.editTextTextPersonName);
        userPass = (EditText)findViewById(R.id.editTextTextPassword);
        progressBar = (ProgressBar)findViewById(R.id.proBarLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String password = userPass.getText().toString().trim();

                if(email.isEmpty()){
                    userEmail.setError("Email is required");
                    userEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.setError("Provide valid email");
                    userEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    userPass.setError("Password is required");
                    userPass.requestFocus();
                    return;
                }

                if(password.length() < 6 ){
                    userPass.setError("Minimum length should be 6 characters");
                    userPass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            // redirects to profile activity
                            startActivity(new Intent(MainActivity.this,gameMainPage.class));

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Failed to login! Try again.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,register.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, gameMainPage.class));
            finish();
        }
    }
}