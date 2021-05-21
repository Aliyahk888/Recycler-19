package com.example.recycler_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recycler_19.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userHomepage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button browse;
    private Button myAccount;
    private Button connectedOrgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        logout=(Button)findViewById(R.id.buttonLogout);
        browse=(Button)findViewById(R.id.browseOrgs);
        myAccount=(Button)findViewById(R.id.myAccount);
        connectedOrgs=(Button)findViewById(R.id.connectedOrgs);

        Toast.makeText(this, "Welcome "+user.getEmail(), Toast.LENGTH_LONG).show();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==logout){
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });
    }
}