package com.example.recycler_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class connectedUsers extends AppCompatActivity {
    RecyclerView recview;
    TextView tw;
    myUserAdapter adapter;
    Button bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_users);

        tw = (TextView) findViewById(R.id.noOrgs);

        bck = (Button) findViewById(R.id.backbutton);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), orgHomepage.class));
            }
        });

        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Deets> options =
                new FirebaseRecyclerOptions.Builder<Deets>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), Deets.class)
                        .build();

        adapter = new myUserAdapter(options);
        recview.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (recview.getChildCount() == 0) {
                    tw.setVisibility(View.VISIBLE);
                }
            }

        }, 3500);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}