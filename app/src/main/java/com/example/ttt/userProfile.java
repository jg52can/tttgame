package com.example.ttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class userProfile extends AppCompatActivity {
    private Button back,signout;
    private FirebaseUser user;
    private String userID;
    FirebaseFirestore db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        back  = (Button) findViewById(R.id.btn_profile_back);
        db1= FirebaseFirestore.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userProfile.this,gameMainPage.class));
                finish();
            }
        });

        signout = (Button)findViewById(R.id.btnSignOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(userProfile.this, MainActivity.class));
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        final TextView fname = (TextView)findViewById(R.id.txtName);
        final TextView femail = (TextView)findViewById(R.id.txtEmail);
        final TextView fuser = (TextView)findViewById(R.id.txtusernamee);
        final TextView win = (TextView)findViewById(R.id.txtwinnumber);
        final TextView lose = (TextView)findViewById(R.id.txtlosenumber);

        db1.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document:task.getResult()){
                                User user1=document.toObject(User.class);
                                if(user1 != null){
                                    String fullName = user1.getFullname();
                                    String email = user1.getEmail();
                                    String username = user1.getUsername();
                                    String wins = String.valueOf(user1.getWin());
                                    String loses = String.valueOf(user1.getLose());
                                    fname.setText(fullName);
                                    femail.setText(email);
                                    fuser.setText(username);
                                    win.setText(wins);
                                    lose.setText(loses);
                                }
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
    }
}