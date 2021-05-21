package com.example.recycler_19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orgSignup extends AppCompatActivity {

    Button btPicker, next, back;
    RadioGroup covidEnabled, deliveryEnabled, recycleType;
    RadioButton isCovid,isDelivery,type;
    TextView tw;
    EditText name, abt, phone, email, password;
    int PLACE_PICKER_REQUEST = 1;
    String orgName, orgPhone, orgLocation, orgAbout, orgEmail, orgPassword, connectedUsers;




    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_signup);

        mAuth = FirebaseAuth.getInstance();

        btPicker = (Button) findViewById(R.id.bt_picker);
        back = (Button) findViewById(R.id.back_tobrowse);
        next = (Button) findViewById(R.id.btnSave);

        tw = (TextView) findViewById(R.id.eplaceseltext);

        covidEnabled = (RadioGroup) findViewById(R.id.contaminated);
        deliveryEnabled = (RadioGroup) findViewById(R.id.delivery);
        recycleType = (RadioGroup) findViewById(R.id.typeGroup);

        name = (EditText)findViewById(R.id.orgNameInput);
        abt = (EditText) findViewById(R.id.abtInput);
        phone = (EditText)findViewById(R.id.phoneInput);
        email = (EditText)findViewById(R.id.emailInput);
        password = (EditText)findViewById(R.id.passwordInput);




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orgName = name.getText().toString();
                orgAbout = abt.getText().toString();
                orgEmail = email.getText().toString();
                orgPhone = phone.getText().toString();
                orgPassword = password.getText().toString();

                int typeNum = recycleType.getCheckedRadioButtonId();
                type=(RadioButton)findViewById(typeNum);
                String orgType = type.getText().toString();

                int covidSelected = covidEnabled.getCheckedRadioButtonId();
                isCovid=(RadioButton)findViewById(covidSelected);
                String cflagg = isCovid.getText().toString();

                int deliverySelected=deliveryEnabled.getCheckedRadioButtonId();
                isDelivery=(RadioButton)findViewById(deliverySelected);
                String dflagg = isDelivery.getText().toString();


                if(isEmpty(name) || isEmpty(abt)) {
                    Toast.makeText(getApplicationContext(),"Please Enter All fields",Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(userId)){
                    registerUser(orgEmail, orgPassword);
                    createOrg(orgName, orgEmail, orgPhone, orgLocation, orgAbout, orgType, cflagg, dflagg);
                    Toast.makeText(getApplicationContext(),"Organization Created !!!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),orgHomepage.class));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });



        btPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(orgSignup.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });



        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("Organizations");
    }
    /**
     * Creating new user node under 'users'
     */
    private void createOrg(String name, String email, String phone, String location, String about, String type, String cFlag, String dFlag) {
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        connectedUsers = "";

        Organizations org = new Organizations(name, email, phone, location, about, type, connectedUsers,cFlag, dFlag);

        mFirebaseDatabase.child(userId).setValue(org);
        addOrgChangeListener();
    }

    private void addOrgChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Organizations org = dataSnapshot.getValue(Organizations.class);

                // Check for null
                if (org == null) {
                    Log.e(TAG, "Organization data is null!");
                    return;
                }

                Log.e(TAG, "Organization data is changed!" + org.orgName );


                // clear edit text
                //name.setText("");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read Organization", error.toException());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST) {
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(data, this);
                tw.setText("!! Location has been selected !!");
                orgLocation = place.getLatLng().toString();
            }
        }
    }





    private boolean isEmpty(EditText et){
        return et.getText().toString().trim().length() == 0;
    }
    private boolean isEmpty(TextView tw){
        return tw.getText().toString().trim().length() == 0;
    }

    private void registerUser(String uemail, String upassword) {

        String email = uemail.trim();
        String password = upassword.trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter E-mail", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), orgHomepage.class));
                } else {
                    Toast.makeText(orgSignup.this, "Registration error!", Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}
