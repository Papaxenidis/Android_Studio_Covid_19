package com.example.covid_examiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    SharedPreferences preferences;
    DatabaseReference ref;

    EditText e1,e2;
    TextView textView;
    Button b1;
    int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        e1 = findViewById(R.id.ed1);
        e2 = findViewById(R.id.ed2);
        e1.setText("");
        e2.setText("");
        b1 = findViewById(R.id.button6);
        textView = findViewById(R.id.text_platforma);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //>>
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
            e1.setHint("1st Code");
            e2.setHint("2d Code");
            b1.setText("Enter");
            textView.setText("ADMIN\nENTRANCE\nPLATFORM");

        }








        //ΕΛΕΓΧΟΣ ΚΩΔΙΚΩΝ ΕΙΣΟΔΟΥ

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String cod1 = e1.getText().toString();
                String cod2 = e2.getText().toString();//ΛΗΨΗ ΔΕΔΟΜΕΝΩΝ

                //>>
                if (cod1.isEmpty()) {
                    if(value==2)
                    {
                        e1.setError("The field is mandatory");

                    }else
                    {
                        e1.setError("Το πεδίο είναι υποχρεωτικό");

                    }
                    e1.requestFocus();
                    return;
                }


                //>>
                if (cod2.isEmpty()) {
                    if(value==2)
                    {
                        e2.setError("The field is mandatory");
                    }else
                    {
                        e2.setError("Το πεδίο είναι υποχρεωτικό");

                    }
                    e2.requestFocus();
                    return;
                }



                //ΧΡΗΣΗ ΤΟΥ REF ΓΙΑ ΠΡΟΣΠΕΛΑΣΗ ΤΗΣ ΒΑΣΗΣ

                ref = FirebaseDatabase.getInstance().getReference().child("Στοιχεία Διαχειριστή");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String y = snapshot.child("Κωδικοί").child("Κωδικός 1").getValue().toString();
                        String x = snapshot.child("Κωδικοί").child("Κωδικός 2").getValue().toString();

                        if(cod1.equals(y)&&cod2.equals(x))
                        {
                            Intent i = new Intent(Profile.this, profile_Admin.class);
                            i.putExtra("key",value);
                            startActivity(i);

                        }









                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });

            }
        });





            }




    }