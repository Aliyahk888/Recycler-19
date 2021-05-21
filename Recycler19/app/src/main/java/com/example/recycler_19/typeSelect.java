package com.example.recycler_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class typeSelect extends AppCompatActivity {

    private Button backButton, paperButton, plasticButton, medicalButton,organicButton, ewasteButton, otherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_select);

        backButton=(Button)findViewById(R.id.backButton);
        paperButton=(Button)findViewById(R.id.paperButton);
        plasticButton=(Button)findViewById(R.id.plasticButton);
        medicalButton=(Button)findViewById(R.id.medicineButton);
        organicButton=(Button)findViewById(R.id.organicButton);
        ewasteButton=(Button)findViewById(R.id.ewasteButton);
        otherButton=(Button)findViewById(R.id.otherButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), userHomepage.class));
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeSelect.this, browseOrgs.class);
                intent.putExtra("Type", "Paper Waste");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), browseOrgs.class));
            }
        });

        plasticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeSelect.this, browseOrgs.class);
                intent.putExtra("Type", "Plastic Waste");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), browseOrgs.class));
            }
        });

        medicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeSelect.this, browseOrgs.class);
                intent.putExtra("Type", "Medical Waste");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), browseOrgs.class));
            }
        });

        organicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeSelect.this, browseOrgs.class);
                intent.putExtra("Type", "Organic Waste");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), browseOrgs.class));
            }
        });

        ewasteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeSelect.this, browseOrgs.class);
                intent.putExtra("Type", "E-Waste");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), browseOrgs.class));
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(typeSelect.this, browseOrgs.class);
                intent.putExtra("Type", "Other Waste");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), browseOrgs.class));
            }
        });
    }
}