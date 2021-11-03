package com.example.covid_examiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class profile_Admin extends AppCompatActivity {

    FirebaseAuth ref;

    int value;
    ImageView iexodos,dimiourgia,diagrafi,enimerosi,stats;
    TextView t_make,t_exodos,t_diagrafi,t_enimerosi,t_stats,admin_banner;



    //ΤΟ ACTIVITY ΣΥΝΤΕΛΕΙΤΑΙ ΑΠΟ ΑΠΛΕΣ ΜΕΤΑΒΙΒΑΣΕΙΣ(INTENTS)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__admin);

        iexodos = findViewById(R.id.imageView6);
        dimiourgia = findViewById(R.id.imageView4);
        diagrafi = findViewById(R.id.imageView5);
        enimerosi = findViewById(R.id.imageView8);
        stats = findViewById(R.id.imageView7);

        t_make = findViewById(R.id.textView);
        t_diagrafi = findViewById(R.id.textView4);
        t_stats = findViewById(R.id.textView5);
        t_enimerosi = findViewById(R.id.textView6);
        t_exodos = findViewById(R.id.textView7);
        admin_banner = findViewById(R.id.textView13);
        ref = FirebaseAuth.getInstance();


        //>>
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
            t_make.setText(" Create\nemployee");
            t_diagrafi.setText(" Delete\nemployee");
            t_stats.setText("Statistics");
            t_exodos.setText("Log out");
            t_enimerosi.setText("Change codes");

        }
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile_Admin.this, statistika.class);
                i.putExtra("key", value);
                startActivity(i);

            }
        });

        enimerosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(profile_Admin.this, ChangeCode.class);
                i.putExtra("key", value);
                startActivity(i);

            }
        });


        diagrafi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile_Admin.this, diagrafi_upallilou.class);
                i.putExtra("key", value);
                startActivity(i);
            }
        });

        iexodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref = FirebaseAuth.getInstance();
                ref.signOut();

                Intent in = new Intent(profile_Admin.this, MainActivity.class);
                startActivity(in);
            }
        });


        dimiourgia.setOnClickListener(new View.OnClickListener() {//N
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profile_Admin.this, make_upallilos.class);
                i.putExtra("key",value);
                startActivity(i);
            }
        });
    }




}