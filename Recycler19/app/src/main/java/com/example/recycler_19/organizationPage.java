package com.example.recycler_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class organizationPage extends AppCompatActivity {

    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference userDatabase;
    private DatabaseReference organizationDatabase;
    TextView orgName, orgAbout, orgType;
    LinearLayout cLayout, dLayout;
    Button back, call, maps, connect;
    String clientName, clientPhone;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_page);

        builder = new AlertDialog.Builder(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        userDatabase = mFirebaseInstance.getReference("Users");
        organizationDatabase = mFirebaseInstance.getReference("Organizations");

        back=(Button)findViewById(R.id.backbutts);
        call=(Button)findViewById(R.id.callOrg);
        maps=(Button)findViewById(R.id.mapOrg);
        connect=(Button)findViewById(R.id.connectWithOrg);

        orgName=(TextView)findViewById(R.id.orgName);
        orgType=(TextView)findViewById(R.id.orgRecycleType);
        orgAbout=(TextView)findViewById(R.id.orgAbout);

        cLayout=(LinearLayout)findViewById(R.id.covidLayout);
        dLayout=(LinearLayout)findViewById(R.id.deliveryLayout);


        final Bundle b = getIntent().getExtras();
        String nameOrg= b.getString("Name");
        String typeOrg= b.getString("Type");
        String phoneOrg= b.getString("Contact");
        String aboutOrg= b.getString("About");
        String whereOrg= b.getString("Location");
        String covidOrg= b.getString("isCovid");
        String deliveryOrg= b.getString("isDelivery");

        orgName.setText(nameOrg);
        orgType.setText(typeOrg);
        orgAbout.setText(aboutOrg);

        if(covidOrg.equals("Yes"))
            cLayout.setVisibility(View.VISIBLE);

        if(deliveryOrg.equals("Yes"))
            dLayout.setVisibility(View.VISIBLE);

        final String coordinates = whereOrg.substring(whereOrg.indexOf('(')+1,whereOrg.indexOf(')'));

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mapUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneOrg));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), typeSelect.class));
            }
        });

        final FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        final String userEmail = user1.getEmail();

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if (childSnapshot.child("email").getValue().equals(userEmail)) {
                        String parent = childSnapshot.getKey();
                        clientName=snapshot.child(parent).child("fullName").getValue().toString();
                        clientPhone=snapshot.child(parent).child("phone").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to connect with this Organization? Your contact details will be made visible to them !")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                organizationDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                            if(childSnapshot.child("orgName").getValue().equals(nameOrg)) {
                                                String parent = childSnapshot.getKey();
                                                if (childSnapshot.child("connectedUsers").child(clientPhone).exists()){
                                                    Toast.makeText(getApplicationContext(), "You are already connected with this Organization !!", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    organizationDatabase.child(parent).child("connectedUsers").child(clientPhone).child("Name").setValue(clientName);
                                                    organizationDatabase.child(parent).child("connectedUsers").child(clientPhone).child("Complete").setValue("No");
                                                    Toast.makeText(getApplicationContext(),"Connection Successful",Toast.LENGTH_LONG).show();
                                                    updateUserDatabase(phoneOrg, nameOrg, clientPhone);
                                                    startActivity(new Intent(getApplicationContext(), userHomepage.class));
                                                }
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("- Consent Form -");
                alert.show();
            }
        });
    }

    public void updateUserDatabase(String ophone, String oname, String uphone){
        userDatabase = mFirebaseInstance.getReference("Users");
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    if(childSnapshot.child("phone").getValue().equals(uphone)) {
                        String parent = childSnapshot.getKey();
                        userDatabase.child(parent).child("connectedOrganizations").child(ophone).child("OrgName").setValue(oname);
                        userDatabase.child(parent).child("connectedOrganizations").child(ophone).child("Complete").setValue("No");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}