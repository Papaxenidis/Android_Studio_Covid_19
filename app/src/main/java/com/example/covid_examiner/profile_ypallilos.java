package com.example.covid_examiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class profile_ypallilos extends AppCompatActivity {

    ImageView iexodos,iasthenis,ienimerosi;
    int value;
    FirebaseAuth ref;

    TextView katathesi,aposundesi,memo,titlos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ypallilos);

        katathesi = findViewById(R.id.textView8);
        aposundesi = findViewById(R.id.textView11);
        titlos = findViewById(R.id.textView12);
        memo = findViewById(R.id.textView10);
        ref = FirebaseAuth.getInstance();


        //ΤΟ ACTIVITY ΣΥΝΤΕΛΕΙΤΑΙ ΑΠΟ ΑΠΛΕΣ ΜΕΤΑΒΙΒΑΣΕΙΣ(INTENTS)



        //Αυτό το τμήμα κώδικως συναντάτε σχεδόν σε κάθε Activity,και υπολογίζει την γλώσσα του UI****

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
            katathesi.setText("Patient \ndeposit");
            aposundesi.setText("Log out");
            memo.setText("Inform \npatient");
            titlos.setText("Employee");
        }
        //**************************************************



        iexodos = findViewById(R.id.imageView9);
        iasthenis = findViewById(R.id.imageView3);
        ienimerosi = findViewById(R.id.imageView10);


        iexodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref = FirebaseAuth.getInstance();
                ref.signOut();


                Intent in = new Intent(profile_ypallilos.this, MainActivity.class);
                startActivity(in);
            }
        });

        iasthenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile_ypallilos.this, asthenis.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });

        ienimerosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile_ypallilos.this, enimerosi.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });




    }
}