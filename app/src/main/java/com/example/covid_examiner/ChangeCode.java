package com.example.covid_examiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeCode extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Κωδικοί");

    EditText edkwd1,edkwd2,edkwd1ep,edkwd2ep;
    TextView titlos;
    Button ok,clean;
    int value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_code);


        titlos = findViewById(R.id.textView3);
        clean = findViewById(R.id.button_clean);

        edkwd1 = findViewById(R.id.code1);
        edkwd2 = findViewById(R.id.code2);
        edkwd1ep = findViewById(R.id.code1epan);
        edkwd2ep = findViewById(R.id.code2epan);
        ok = findViewById(R.id.button_ok);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
                titlos.setText("Please type codes:");
                edkwd1.setHint("Code 1");
                edkwd1ep.setHint("Repeat code 1");
                edkwd2.setHint("Code 2");
                edkwd2ep.setHint("Repeat code 2");
                ok.setText("Change");
                clean.setText("Clear");
        }

        //ΚΟΥΜΠΙ ΚΑΘΑΡΙΣΜΟΥ

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edkwd1.setText("");
                edkwd2.setText("");
                edkwd1ep.setText("");
                edkwd2ep.setText("");
            }
        });

        //ΚΟΥΜΠΙ ΟΡΙΣΤΙΚΟΠΟΙΗΣΗΣ ΑΛΛΑΓΗΣ

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String k1 = edkwd1.getText().toString();
                String k2 = edkwd2.getText().toString();
                String k1e = edkwd1ep.getText().toString();
                String k2e = edkwd2ep.getText().toString();//ΛΗΨΗ ΔΕΔΟΜΕΝΩΝ


            //ΕΛΕΓΧΟΣ ΓΙΑ ΤΗΝ ΤΑΥΤΗΣΗ ΤΩΝ ΚΩΔΙΚΩΝ

                if(!k1.equals(k1e))
                {

                    //ΕΛΕΓΧΟΣ ΓΙΑ ΤΗΝ ΓΛΩΣΣΑ ΕΚΔΟΣΗΣ ΕΙΔΟΠΟΙΗΤΙΚΟΥ ΜΗΝΥΜΑΤΟΣ
                    if(value==2)
                    {
                        edkwd1.setError("The codes don't match");

                    }else
                    {
                        edkwd1.setError("Οι κωδικοί δεν ταυτίζονται");

                    }
                    edkwd1.requestFocus();
                    return;

                }
                //>>
                if(!k2.equals(k2e))
                {
                    //>>
                    if(value==2)
                    {
                        edkwd2.setError("The codes don't match");

                    }else
                    {
                        edkwd2.setError("Οι κωδικοί δεν ταυτίζονται");

                    }
                    edkwd2.requestFocus();
                    return;
                }




                //ΛΗΨΗ DATABASE REFERENCE ΓΙΑ ΤΗΝ ΠΡΟΣΠΕΛΑΣΗ ΤΟΥ ΚΟΜΒΟΥ ΚΩΔΙΚΟΙ

                    root = db.getReference().child("Στοιχεία Διαχειριστή").child("Κωδικοί");

                //ΕΝΑΠΟΘΕΣΗ ΝΕΩΝ ΣΤΟΙΧΕΙΩΝ ΣΤΗΝ ΒΑΣΗ

                    root.child("Κωδικός 1").setValue(k1);
                    root.child("Κωδικός 2").setValue(k2);

                    //>>
                    if(value==2)
                    {
                        Toast.makeText(ChangeCode.this, "Change has been made!", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(ChangeCode.this, "Η αλλαγή έγινε!", Toast.LENGTH_SHORT).show();

                    }


                    //ΕΠΙΣΤΡΟΦΗ ΣΤΟ ΠΡΟΦΙΛ ΤΟΥ ADMIN

                     Intent i = new Intent(ChangeCode.this, profile_Admin.class);
                     i.putExtra("key", value);
                     startActivity(i);









            }
        });







    }
}