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

public class browseOrgs extends AppCompatActivity {
    String recType;
    RecyclerView recview;
    myAdapter adapter;
    Button bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_orgs);

        bck = (Button) findViewById(R.id.backbutton);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), typeSelect.class));
            }
        });


        recType = getIntent().getStringExtra("Type");

        recview = (RecyclerView) findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Organizations> options =
                new FirebaseRecyclerOptions.Builder<Organizations>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Organizations"), Organizations.class)
                        .build();

        adapter = new myAdapter(options, recType);
        recview.setAdapter(adapter);


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