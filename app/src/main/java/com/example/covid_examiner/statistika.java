package com.example.covid_examiner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLTransactionRollbackException;

public class statistika extends AppCompatActivity {

    DatabaseReference ref;

    TextView geniko_pl,giraios,neoteros,mos,pos_antres,pos_gunaikes,txt_geniko;
    TextView  txt_mo,txt_gir,txt_neo,txt_gyn,txt_antres;
    int s=0,m=0,gene_people=0,pl_gun=0,pl_ant=0,value;
    String pl = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika);


        txt_geniko = findViewById(R.id.textView27);
        txt_mo = findViewById(R.id.textView29);
        txt_gir = findViewById(R.id.textView28);
        txt_neo = findViewById(R.id.textView30);
        txt_gyn = findViewById(R.id.textView31);
        txt_antres = findViewById(R.id.textView32);




        geniko_pl = findViewById(R.id.tgenplithos);
        giraios = findViewById(R.id.tgiraioteros);
        neoteros = findViewById(R.id.tneoteros);
        mos = findViewById(R.id.tmo);
        pos_antres = findViewById(R.id.tandres);
        pos_gunaikes = findViewById(R.id.tgynaikes);

        //>>
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("key");
        }

        if(value==2)
        {
            txt_geniko.setText("Total patient number:");
            txt_mo.setText("Average age number:");
            txt_gir.setText("Oldest age:");
            txt_neo.setText("Youngest age:");
            txt_gyn.setText("Women patient's rate:");
            txt_antres.setText("Men patient's rate:");
        }




        ref = FirebaseDatabase.getInstance().getReference().child("Ασθενείς");
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String k;
                        String fulo;
                        int x=1;
                        int max = 0;
                        int min = 35000;


                    //ΣΥΝΕΧΗΣ ΣΑΡΩΣΗ ΤΩΝ ΣΤΟΙΧΕΙΩΝ ΤΗΣ REALTIME
                        for(DataSnapshot snapshot1:snapshot.getChildren()) {

                            gene_people++;//λήχη γενικού πλήθους

                            fulo = snapshot.child(pl).child("Φύλο").getValue().toString();//ΛΗΨΗ ΦΎΛΟΥ
                            k = snapshot.child(pl).child("Ηλικία").getValue().toString();//ΛΗΨΗ ΗΛΙΚΙΑΣ
                            m = Integer.parseInt(k);
                            max =Math.max(max,m);
                            min = Math.min(min,m);
                            s = s + m;
                            x++;
                            int temp = Integer.parseInt(pl);
                            temp++;
                            pl = String.valueOf(temp);

                            if(fulo.equals("Θύλη"))
                            {
                                pl_gun++;
                            }
                            if(fulo.equals("Άρρεν"))
                            {
                                pl_ant++;
                            }



                        }






                //ΠΡΑΞΕΙΣ
                        double plg = ((double)pl_gun/(double)gene_people)*100;
                        double pla = ((double)pl_ant/(double)gene_people)*100;

                        int pososto_gun = (int)plg;
                        int pososto_ant = (int)pla;

                        double die = (double)s/(double)gene_people;
                        int e = (int)die;
                        String mesos_oros = String.valueOf(e);

                 //ΠΑΡΟΥΣΙΑΣΕΙΣ

                        mos.setText(mesos_oros);
                        geniko_pl.setText(String.valueOf(gene_people));
                        giraios.setText(String.valueOf(max));
                        neoteros.setText(String.valueOf(min));
                        pos_antres.setText(String.valueOf(pososto_ant)+"%");
                        pos_gunaikes.setText(String.valueOf(pososto_gun)+"%");




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });










    }
}