package com.example.recycler_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class orgHomepage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button connUsers;
    private Button myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_homepage);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }



        FirebaseUser user = firebaseAuth.getCurrentUser();
        logout=(Button)findViewById(R.id.buttonLogout);
        myAccount=(Button)findViewById(R.id.orgAccount);
        connUsers=(Button)findViewById(R.id.connectedUsers);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==logout){
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(),orgLogin.class));
                }
            }
        });

        connUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), connectedUsers.class));
            }
        });
    }
}