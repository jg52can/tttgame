package com.example.ttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class gameMainPage extends AppCompatActivity {
    private Button pc, online, friends,exit,profile;
    FirebaseUser user;
    String userID;
    private FirebaseAuth mAuth;
    FirebaseFirestore db1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main_page);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db1= FirebaseFirestore.getInstance();
        userID = user.getUid();



        db1.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                User user1=document.toObject(User.class);
                                String names = user1.getUsername();
                                String emails = user1.getEmail();
                            }

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        pc  = (Button) findViewById(R.id.btn_user_computer);
        online  = (Button) findViewById(R.id.btn_user_play_online);
        friends  = (Button) findViewById(R.id.btn_user_play_with_friends);
        exit  = (Button) findViewById(R.id.btn_user_exit);
        profile  = (Button) findViewById(R.id.btn_user_profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(gameMainPage.this,userProfile.class));
                finish();
            }
        });
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(gameMainPage.this,playWithComputer.class));
                finish();
            }
        });

        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(gameMainPage.this,playOnline.class));
                finish();
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(gameMainPage.this,playWithFriend.class));
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}