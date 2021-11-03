package com.example.covid_examiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class asthenis extends AppCompatActivity implements LocationListener {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Aσθενείς");


    DatabaseReference ref;


    EditText e1,e2,e3,e5,e4,eage,egender;
    SharedPreferences preferences;
    Button b2,but,save,clear;
    LocationManager locationManager;
    TextView t1,t2,t3,t4,t5,tage,tgender;
    int plithos=0,value;
    String imerominia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asthenis);


        e1 = findViewById(R.id.editonoma);
        e2 = findViewById(R.id.editepitheto);
        e3 = findViewById(R.id.editarithmos);
        e4 = findViewById(R.id.edittopos);
        e5 = findViewById(R.id.editwra);
        eage = findViewById(R.id.edittopos2);
        egender = findViewById(R.id.edittopos3);

        tage = findViewById(R.id.textView9);
        tgender = findViewById(R.id.textView26);




        b2 = findViewById(R.id.clean_button);
        t1 = findViewById(R.id.textView19);
        t2 = findViewById(R.id.textView18);
        t3 = findViewById(R.id.textView17);
        t4 = findViewById(R.id.textView15);
        t5 = findViewById(R.id.textView16);
        but = findViewById(R.id.button);
        save = findViewById(R.id.button2);
        clear = findViewById(R.id.button3);


        e3.setKeyListener(null);
        e4.setKeyListener(null);
        e5.setKeyListener(null);

        //preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());--->SP----<
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
            t4.setText("Name");
            t5.setText("Last name");
            t1.setText("Serial number");
            t2.setText("Location");
            t3.setText("Date/time of Diagnosis");
            tage.setText("Age");
            tgender.setText("Gender");

            tgender.setHint("Type f(for female,m(for male)");


            but.setText("Confirm");
            b2.setText("Clear");
            save.setText("Save");
            clear.setText("Clear");


        }





        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
                e2.setText("");
                eage.setText("");
                egender.setText("");
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Ασθενείς");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()) {


                    plithos=plithos+1;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




               // String temp2=preferences.getString("ar_katataxis", "0"); ---->SP<----




                String xS = String.valueOf(plithos);

                root = db.getReference().child("Ασθενείς").child(xS);


                String Name = e1.getText().toString();
                String Lname = e2.getText().toString();
                //String katataxi = temp2; ---->SP<----
                String katataxi =xS;
                String date_time = e3.getText().toString();
                String f = egender.getText().toString();
                String ag = eage.getText().toString();
                String topos = e4.getText().toString();




                root.child("Όνομα").setValue(Name);
                root.child("Επίθετο").setValue(Lname);
                root.child("Αριθμός Κατάταξης").setValue(katataxi);
                root.child("Ημερομηνία και Ώρα").setValue(date_time);
                root.child("Τοποθεσία").setValue(topos);
                if(f.equals("f"))
                {
                    root.child("Φύλο").setValue("Θύλη");

                }
                if(f.equals("m"))
                {
                    root.child("Φύλο").setValue("Άρρεν");

                }
                root.child("Ηλικία").setValue(ag);



                if(value==2)
                {
                    Toast.makeText(asthenis.this, "Successful insertion!", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(asthenis.this, "Επιτυχής Εισαγωγή!", Toast.LENGTH_SHORT).show();

                }

              //  SharedPreferences.Editor editor = preferences.edit();
               // String arithmos = String.valueOf(plithos);
               // editor.putString("ar_katataxis",arithmos);
               // editor.apply();



                Intent i = new Intent(asthenis.this, profile_ypallilos.class);
                i.putExtra("key", value);
                startActivity(i);


            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");
                eage.setText("");
                egender.setText("");

                e3.setVisibility(View.INVISIBLE);
                e4.setVisibility(View.INVISIBLE);
                e5.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);

                b2.setVisibility(View.VISIBLE);
                but.setVisibility(View.VISIBLE);

            }
        });





    }




    public void gps(View view) {


        String on = e1.getText().toString();
        String ep = e2.getText().toString();
        String iligia =eage.getText().toString();
        String filo = egender.getText().toString();



        if(on.isEmpty())
        {
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

        if(ep.isEmpty())
        {
            if(value==2)
            {
                e2.setError("The field is mandatory");

            }else
            {
                e2.setError("Το πεδίο είναι υποχρεωτικό");

            }
            return;
        }

        if(filo.isEmpty())
        {
            if(value==2)
            {
                egender.setError("The field is mandatory");

            }else
            {
                egender.setError("Το πεδίο είναι υποχρεωτικό");

            }


            egender.requestFocus();
            return;
        }

        if(iligia.isEmpty())
        {
            if(value==2)
            {
                eage.setError("The field is mandatory");

            }else
            {
                eage.setError("Το πεδίο είναι υποχρεωτικό");

            }
            eage.requestFocus();
            return;
        }

        e3.setVisibility(View.VISIBLE);
        e4.setVisibility(View.VISIBLE);
        e5.setVisibility(View.VISIBLE);

        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.VISIBLE);
        t3.setVisibility(View.VISIBLE);


        save.setVisibility(View.VISIBLE);
        clear.setVisibility(View.VISIBLE);


        but.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.
                    requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},234);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                this);
       // locationManager.removeUpdates(this);

        wra_arithmos();

    }



    public void wra_arithmos()
    {

        Date currenttime = Calendar.getInstance().getTime();
        String imer = currenttime.toString();
        imerominia = (imer.substring(0,20));


        // String temp2=preferences.getString("ar_katataxis", "0"); ---->SP<----


        String plithos_epanalipsis = String.valueOf(plithos);

        e5.setText(plithos_epanalipsis);
        e3.setText(imerominia);

       /* SharedPreferences.Editor editor = preferences.edit();---->SP<----
        temp_ar = Integer.parseInt(temp2);---->SP<----
        temp_ar=temp_ar+1;---->SP<----
        String arithmos = String.valueOf(temp_ar);---->SP<----
        editor.putString("ar_katataxis",arithmos);---->SP<----
        editor.apply();---->SP<----*/


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        double longitude = location.getLongitude(); //getting location
        double latitude = location.getLatitude();
        Geocoder geocoder = new Geocoder(this, Locale.forLanguageTag("EL"));
        try {
            List<Address> addresses= geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses!=null && addresses.size()>0) {

                String myLocation = addresses.get(0).getLocality();
                String neo = myLocation;


                neo = neo.replaceAll(" ","");
                //------
              /*  int index=0;
                char[] ch = new char[neo.length()];
                for (int i = 0; i < neo.length(); i++) {
                    ch[i] = neo.charAt(i);
                }


                for(int i=1;i<neo.length();i++)
                {

                    if(Character.isUpperCase(ch[i]))
                    {
                        index = i;
                    }


                }

               if(index!=0)
               {
                   char[] teliko = new char[neo.length()+1];
                   for(int i=0;i<neo.length()+1;i++)
                   {

                       teliko[i]=

                   }
               }*/









//-----
                e4.setText(neo);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        locationManager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }



}