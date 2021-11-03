package com.example.covid_examiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class diagrafi_upallilou extends AppCompatActivity {

    Button button;
    EditText editText, editText1;
    private FirebaseAuth mAuth;
    int value;
    TextView txtepikefalida,txtminima,txtemail,txtpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagrafi_upallilou);

        //*************************************************************

        mAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.button);
        txtepikefalida = findViewById(R.id.textView2);
        txtminima = findViewById(R.id.textView4);
        txtemail = findViewById(R.id.textView3);
        txtpass = findViewById(R.id.textView);

        //>>

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
            txtepikefalida.setText("Delete employee");
            txtminima.setText("Insert data of the employee \n for deletion");
            txtpass.setText("Password");
            button.setText("Delete");

        }



//ΤΑΥΤΟΠΟΙΗΣΗ ΤΟΥ ΥΠΑΛΛΗΛΟΥ ΜΕ EMAIL ΚΑΙ ΚΩΔΙΚΟ

        button.setOnClickListener((view)->{
            editText = findViewById(R.id.editTextTextEmailAddress);
            editText1 = findViewById(R.id.editTextTextPassword);
            mAuth.signInWithEmailAndPassword(editText.getText().toString(), editText1.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                String em = editText.getText().toString();
                String pass = editText1.getText().toString();



                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //ΕΛΕΓΧΟΙ
                     if(em.isEmpty())
                     {
                         //>>
                         if(value==2)
                         {
                             editText.setError("The field is mandatory");

                         }else
                         {
                             editText.setError("Το πεδίο είναι υποχρεωτικό");

                         }


                         editText.requestFocus();
                         return;
                     }


                     //ΕΛΕΓΧΟΣ ΥΠΑΡΞΗΣ ΤΟΥ DOMAN ΝΑΜΕ ΤΟΥ ΔΙΔΩΜΕΝΟΥ EMAIL
                    if(!Patterns.EMAIL_ADDRESS.matcher(em).matches())
                    {
                        //>>
                        if(value==2) {
                            editText.setError("Please provide valid email adress address");
                        }else
                        {
                            editText.setError("Δώστε έγγυρο email");

                        }
                        editText.requestFocus();
                        return;


                    }






                //>>
                    if(pass.isEmpty()) {
                        if(value==2)
                        {
                            editText1.setError("The field is mandatory");

                        }else
                        {
                            editText1.setError("Το πεδίο είναι υποχρεωτικό");

                        }

                        editText1.requestFocus();
                        return;
                    }

                //ΕΛΕΓΧΟΣ ΕΠΙΤΥΧΟΥΣ ΤΑΥΤΗΣΗΣ

                    if (task.isSuccessful()) {


                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        AuthCredential credential = EmailAuthProvider
                                .getCredential(editText.getText().toString(), editText1.getText().toString());

                        //ΕΠΑΝΕΞΕΤΑΣΗ
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //ΔΙΑΓΡΑΦΗ
                                        user.delete()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            //>>
                                                            if(value==2)
                                                            {
                                                                Toast.makeText(getApplicationContext(),"User deleted successfully", Toast.LENGTH_LONG).show();

                                                            }else
                                                            {
                                                                Toast.makeText(getApplicationContext(),"Ο χρήστης διαγράφηκε με επιτυχία", Toast.LENGTH_LONG).show();

                                                            }




                                                            //ΕΠΙΣΤΡΟΦΗ ΣΤΟ ΠΡΟΦΙΛ ΤΟΥ ADMIN
                                                            Intent i = new Intent(diagrafi_upallilou.this, profile_Admin.class);
                                                            i.putExtra("key", value);
                                                            startActivity(i);
                                                        }
                                                    }
                                                });

                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        });

    }
}