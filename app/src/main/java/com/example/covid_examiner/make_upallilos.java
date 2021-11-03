package com.example.covid_examiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class make_upallilos extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences preferences;
    EditText onoma,epitheto,ar_kat,til,meil;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    int value;
    TextView tname,tlname,tmail,tserial,tphone,banner;
    Button ok,clear;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Υπάλληλοι");
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_upallilos);

        onoma = findViewById(R.id.edonoma);
        epitheto = findViewById(R.id.edepitheto);
        ar_kat = findViewById(R.id.edarkat);
        til = findViewById(R.id.edtil);
        meil = findViewById(R.id.edemail);

        tname = findViewById(R.id.textView20);
        tlname = findViewById(R.id.textView21);
        tmail = findViewById(R.id.textView25);
        tserial = findViewById(R.id.textView22);
        tphone = findViewById(R.id.textView23);
        banner = findViewById(R.id.textView24);

        ok = findViewById(R.id.button4);
        clear = findViewById(R.id.button5);

        ar_kat.setKeyListener(null);

        mAuth = FirebaseAuth.getInstance();

        //ΠΛΗΚΤΡΟ ΚΑΘΑΡΙΣΜΟΥ
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onoma.setText("");
                epitheto.setText("");
                ar_kat.setText("");
                til.setText("");

            }
        });

        //>>
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {

            tname.setText("Name");
            tlname.setText("Last name");
            tphone.setText("Phone number");
            tmail.setText("Email");
            tserial.setText("Seria number");
            ar_kat.setHint("Autocompleting");
            banner.setText("Create \n employee ");
            ok.setText("Create");
            clear.setText("Clear");


        }

        //ΠΛΗΚΤΡΟ ΔΗΜΙΟΥΡΓΙΑΣ
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createuser();
            }
        });

        //ΕΛΕΓΧΟΣ ΑΔΕΙΑΣ SMS

        if(checkPermission(Manifest.permission.SEND_SMS))
        {

        }else
        {
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.SEND_SMS}
                    ,SEND_SMS_PERMISSION_REQUEST_CODE);
        }


    }

    private void createuser() {

        //ΛΗΨΗ ΤΥΧΑΙΟΥ ΑΡΙΘΜΟΥ ΓΙΑ ΤΟΝ ΑΡΙΣΘΜΟ ΚΑΤΑΤΑΞΗΣ ΚΑΙ ΤΟΝ ΚΩΔΙΚΟ ΤΟΥ ΥΠΑΛΛΗΛΟΥ
        Random rand = new Random();
        int rand_int1 = rand.nextInt(100000);
        int temp = rand.nextInt(10000000);


        String Name = onoma.getText().toString();
        String Lname = epitheto.getText().toString();
        String katataxi =String.valueOf(rand_int1);
        String tilefwno = til.getText().toString();
        String email = meil.getText().toString();

        //ΚΩΔΙΚΟΣ ΥΠΑΛΛΗΛΟΥ ΩΣ ΤΥΧΑΙΩΣ ΠΑΡΑΓΩΜΕΝΟΣ
        String password = String.valueOf(temp);

        //>>
       if(Name.isEmpty())
        {
            if(value==2) {
                onoma.setError("The field is mandatory");
            }else
            {
                onoma.setError("Το πεδίο είναι υποχρεωτικό");

            }

            onoma.requestFocus();
            return;
        }

        //>>
        if(Lname.isEmpty())
        {
            if(value==2) {
                epitheto.setError("The field is mandatory");
            }else
            {
                epitheto.setError("Το πεδίο είναι υποχρεωτικό");

            }
            return;
        }

        //>>
        if(tilefwno.isEmpty())
        {
            if(value==2) {
                til.setError("The field is mandatory");
            }else
            {
                til.setError("Το πεδίο είναι υποχρεωτικό");

            }



            til.requestFocus();
            return;
        }

        //>>
        if(email.isEmpty())
        {
            if(value==2) {
                meil.setError("The field is mandatory");
            }else
            {
                meil.setError("Το πεδίο είναι υποχρεωτικό");

            }
            meil.requestFocus();
            return;
        }

        //>>
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            if(value==2) {
                meil.setError("Please provide valid email adress address");
            }else
            {
                meil.setError("Δώστε έγγυρο email");

            }
            meil.requestFocus();
            return;


        }







        //Κλήση της createUser με το αντικείμενο της FirebaseAuth
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {

                            //ΧΡΗΣΗ ΤΗΣ ΒΟΗΘΗΤΙΚΗΣ ΚΛΑΣΗΣ USER ΚΑΙ ΠΙΟ ΣΥΓΚΕΚΡΙΜΕΝΑ ΤΟΥ CONSTRUCTOR ΤΗΣ

                            User user = new User(Name,Lname,email,katataxi,tilefwno,password);

                            FirebaseDatabase.getInstance().getReference("Υπάλληλοι")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    //>>
                                    if(task.isSuccessful())
                                    {
                                        if(value==2)
                                        {
                                            Toast.makeText(make_upallilos.this,"The user was inserted successfully!",Toast.LENGTH_LONG).show();

                                        }else
                                        {
                                            Toast.makeText(make_upallilos.this,"Ο χρήστης εισήχθη επιτυχώς!",Toast.LENGTH_LONG).show();

                                        }

                                        //ΚΛΗΣΗ ΤΗΣ SEND
                                        send(password,tilefwno);

                                        //ΜΕΤΑΒΙΒΑΣΗ
                                        Intent i = new Intent(make_upallilos.this, profile_Admin.class);
                                        i.putExtra("key", value);
                                        startActivity(i);


                                    }else
                                    {
                                        //>>
                                        if(value==2)
                                        {
                                            Toast.makeText(make_upallilos.this,"Something went wrong,try again!",Toast.LENGTH_LONG).show();

                                        }else
                                        {
                                            Toast.makeText(make_upallilos.this,"Προέκυψε πρόβλημα,προσπαθήστε ξανά!",Toast.LENGTH_LONG).show();

                                        }



                                    }
                                }
                            });


                        }else {
                            //>>
                            if(value==2)
                            {
                                Toast.makeText(make_upallilos.this,"Something went wrong,try again!",Toast.LENGTH_LONG).show();

                            }else
                            {
                                Toast.makeText(make_upallilos.this,"Προέκυψε πρόβλημα,προσπαθήστε ξανά!",Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });









    }


    @Override
    public void onClick(View v) {

    }



    public boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return  (check== PackageManager.PERMISSION_GRANTED);
    }


    private void send(String password,String tilefwno)
    {
         String phone_num = tilefwno;
         String sms = "Μόλις αποκτήσατε λογαριασμό στην Υπουργική πλατφόρμα Covid_examiner,για τον ελεγχο "
            +"και την επιτήρηση του Ιού Covid-19.Για να συνδεθείτε,θα χρησιμοποιήσετε το εν ενεργεία email"+
                 "σας καθώς και τον κωδικό "+":"+password;




        if(checkPermission(Manifest.permission.SEND_SMS)  )
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone_num,null,sms,null,null);
            if(value==2)
            {
                Toast.makeText(this, "Message Send!", Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(this, "Μήνυμα εστάλη!", Toast.LENGTH_SHORT).show();

            }
            Intent in = new Intent(make_upallilos.this, profile_Admin.class);
            startActivity(in);

        }else
        {
            if(value==2)
            {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(this, "Άκυρη άδεια!", Toast.LENGTH_SHORT).show();

            }
        }



    }
}