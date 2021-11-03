package com.example.covid_examiner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class enimerosi extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    Button bp,bn,b_send,b_cansel;
    String minima ;
    EditText til;
    TextView phnum;


    int value = 1;
    boolean rr = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enimerosi);


        b_send = findViewById(R.id.apostoli);
        b_cansel = findViewById(R.id.akurosi);
        bp = findViewById(R.id.bth);
        bn = findViewById(R.id.bar);
        phnum = findViewById(R.id.textView14);
        til = findViewById(R.id.editTextTextPersonName3);



        //ΕΛΕΓΧΟΣ ΛΗΨΗΣ ΑΔΕΙΑΣ SMS


        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            bp.setEnabled(true);
            bn.setEnabled(true);
            b_send.setEnabled(true);
        }else
        {
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.SEND_SMS}
                    ,SEND_SMS_PERMISSION_REQUEST_CODE);

        }
        //>>
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
           bp.setText("Possitive");
           bn.setText("Negative");
           phnum.setText("Type Patient's number:");
           til.setHint("Phone number f.e 697..");
           b_send.setText("SEND");
           b_cansel.setText("CANCEL");

        }





        //ΠΑΤΗΜΑ ΠΛΗΚΤΡΟΥ ΘΕΤΙΚΗΣ ΔΙΑΓΝΩΣΗΣ
        bp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thetiko();
            }
        });

        //ΠΑΤΗΜΑ ΠΛΗΚΤΡΟΥ ΑΡΝΗΤΙΚΗΣ ΔΙΑΓΝΩΣΗΣ
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arnitiko();
            }
        });

        //ΠΑΤΗΜΑ ΠΛΗΚΤΡΟΥ ΑΚΥΡΩΣΗΣ
       b_cansel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               b_send.setVisibility(View.INVISIBLE);
               b_cansel.setVisibility(View.INVISIBLE);
               til.setText(" ");


           }
       });


    }

    private void arnitiko() {

        String number = til.getText().toString();//ΛΗΨΗ ΑΡΙΘΜΟΥ


        //>>
        if(number.isEmpty()) {

            if(value==2)
            {
                til.setError("The field is mandatory");

            }

            if (value==1){

                til.setError("Το πεδίο είναι υποχρεωτικό");


            }

            til.requestFocus();
            return;
        }
        //>>
        if(number.length()>10 || number.length()<10)
        {
            if(value==2)
            {
                til.setError("The sum of digits must be exactly ten to belong in the mobile network of Greece");

            }
            if (value==1){

                til.setError("Τα ψηφία πρέπει να είναι 10 ώστε να ανήκουν στο Ελληνικό δίκτυο τηλεφωνίας");


            }
            til.requestFocus();
            return;
        }

        //>>


            if (value == 2) {
                minima = "After processing your blood sample,we concluded that you don't suffer from Covid-19 virus.Keep protecting your self " +
                        "and thank you for your cooperation ";
                rr = false;

            }
            if (value==1) {
                minima = "Κατόπιν εξακρίβωσης,κατεδείχθη πως είστε δεν πάσχετε από τον ιό Covid-19 Συνεχίστε να προφυλάσσεστε και σας ευχαριστούμε για την συνεργασία";

            
        }




        b_send.setVisibility(View.VISIBLE);
        b_cansel.setVisibility(View.VISIBLE);


    }

    private void thetiko() {

        String number = til.getText().toString();//ΛΗΨΗ ΑΡΙΘΜΟΥ

        //>>
        if (number.isEmpty()) {

            if(value==2)
            {
                til.setError("The field is mandatory");

            }
            if (value==1){

                til.setError("Το πεδίο είναι υποχρεωτικό");


            }

            til.requestFocus();
            return;
        }
        //>>
        if(number.length()>10 || number.length()<10)
        {
            if(value==2)
            {
                til.setError("The sum of digits must be exactly ten to belong in the mobile network of Greece");

            }
            if(value==1){

                til.setError("Τα ψηφία πρέπει να είναι 10 ώστε να ανήκουν στο Ελληνικό δίκτυο τηλεφωνίας");


            }
            til.requestFocus();
            return;
        }

        //>>
        if(value==2)
        {
           minima = "Your diagnosis was deemed positive.Please stay at home for the next 14 days.After that,you're required " +
                   "to visit a health center and validate your recovery";

        }
        if(value!=2)
        {
            minima = "Η διάγνωση σας απεφάνθη θετική.Παρακαλούμε να μείνετε σπίτι για τις επόμενες"+
                    "14 ημέρες.Κατόπιν αυτών,οφείλεται να επισκεφτείτε ξανά κάποιο κέντρο υγείας για να επαληθευθεί"+
                    "η ανάρρωσης σας";

        }




        b_send.setVisibility(View.VISIBLE);
        b_cansel.setVisibility(View.VISIBLE);

    }





    //ΑΠΟΣΤΟΛΗ ΜΗΝΥΜΑΤΟΣ
    public void send(View view)
    {
       String phone_num = til.getText().toString();//ΛΗΨΗ ΤΗΛΕΦΩΝΟΥ
       String sms = minima;//ΛΗΨΗ ΜΗΝΥΜΑΤΟΣ ΔΙΑΓΝΩΣΗΣ




        //ΑΠΟΣΤΟΛΗ ΜΗΝΥΜΑΤΟΣ
        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone_num,null,sms,null,null);
            //>>
            if(value==2)
            {
                Toast.makeText(this, "Message send!", Toast.LENGTH_SHORT).show();


            }
            if(value==1)
            {
                Toast.makeText(this, "Μήνυμα εστάλη!", Toast.LENGTH_SHORT).show();


            }

            til.setText("");
            b_send.setVisibility(View.INVISIBLE);
            b_cansel.setVisibility(View.INVISIBLE);




        }else
        {
            //>>
            if(value==2)
            {
                Toast.makeText(this, "Something went wrong,try again", Toast.LENGTH_SHORT).show();

            }
            if(value==1)
            {
                Toast.makeText(this, "Προέκυψε σφάλμα,προσπαθήστε ξανά", Toast.LENGTH_SHORT).show();

            }
        }







    }
    //ΕΠΙΒΕΒΑΙΩΣΗ ΑΔΕΙΑΣ
    public boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this,permission);
        return  (check== PackageManager.PERMISSION_GRANTED);
    }

}