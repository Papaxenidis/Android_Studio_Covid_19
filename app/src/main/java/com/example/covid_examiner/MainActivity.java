package com.example.covid_examiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences preferences;
    EditText  code1, code2;
    TextView admin;
    Button buttonenter;
    ImageView gr, en;
    int lang = 1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gr = findViewById(R.id.imageView2g);
        en = findViewById(R.id.imageViewe);
        code1 = findViewById(R.id.edit_code1);
        code2 = findViewById(R.id.edit_code2);
        admin = findViewById(R.id.textView2);
        buttonenter = findViewById(R.id.koumpi_eisodou);
        mAuth = FirebaseAuth.getInstance();


        code1.setText("");
        code2.setText("");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//**  ΕΠΙΛΟΓΗ ΓΛΩΣΣΑΣ ΤΟΥ UI.ΚΑΤΟΠΙΝ ΑΥΤΟΥ ΤΟΥ ΣΗΜΕΙΟΥ ΣΕ ΚΑΘΕ ACTIVITY ΥΠΑΡΧΕΙ ΔΟΜΗ IF ΠΟΥ ΚΑΘΟΡΙΖΕΙ ΕΝΑ ΤΟ LANG ΕΙΝΑΙ 1(ΕΛΛΗΝΙΚΑ)
//    Η ΕΑΝ ΤΟ LANG ΕΙΝΑΙ 2(ΑΓΓΛΙΚΑ)

        gr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang = 1;
                code2.setHint("Κωδικός");
                buttonenter.setText("Είσοδος");
                admin.setText("○Είσοδος ως Admin");

            }
        });


        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code2.setHint("Password");
                lang = 2;
                buttonenter.setText("Enter");
                admin.setText("○Log in as Admin");
            }
        });

//*****************************************************************

        // ΜΕΤΑΒΙΒΑΣΗ ΓΙΑ ΕΙΣΟΔΟ ΣΤΗΝ ΠΛΑΤΦΟΡΜΑ ADMIN
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                i.putExtra("key", lang);
                startActivity(i);
            }
        });

        //ΜΕΤΑΒΙΒΑΣΗ ΣΤΟ ΠΡΟΦΙΛ ΥΠΑΛΛΗΛΟΥ
        buttonenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_method();
            }
        });


    }

    private void enter_method() {

      //>>
        String email = code1.getText().toString();
        String password = code2.getText().toString();

        //>>
        if (email.isEmpty()) {
            if(lang==2)
            {
                code1.setError("The field is mandatory");

            }else
            {
                code1.setError("Το πεδίο είναι υποχρεωτικό");

            }
            code1.requestFocus();
            return;
        }
        //>>
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            if(lang==2) {
                code1.setError("Please provide valid email adress address");
            }else
            {
                code1.setError("Δώστε έγγυρο email");

            }
            code1.requestFocus();
            return;


        }





        //>>
        if (password.isEmpty()) {

            if(lang==2)
            {
                code2.setError("The field is mandatory");

            }else
            {
                code2.setError("Το πεδίο είναι υποχρεωτικό");

            }
            code2.requestFocus();
            return;
        }





        //ΧΡΗΣΗ ΤΟΥΝ mAuth για επιβεβαιωση από την firebase με Authenication

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent i = new Intent(MainActivity.this, profile_ypallilos.class);
                    i.putExtra("key",lang);
                    startActivity(i);                }
                else
                {

                    //>>
                        if(lang==2) {
                            Toast.makeText(MainActivity.this,"Something went wrong,try again",Toast.LENGTH_LONG).show();
                        }else
                        {
                            Toast.makeText(MainActivity.this,"Αποτυχία σύνδεσης,προσπαθήστε ξανά!",Toast.LENGTH_LONG).show();

                        }
                        code1.requestFocus();
                        return;




                }
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}