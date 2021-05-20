package com.example.ppe4_baptiste;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AfficheVisite extends AppCompatActivity {

    private Integer idVisite;
    private ListView listView;
    private Date ddatereelle;
    private List<VisiteSoin> listeSoin;
    private Visite laVisite;
    private Modele vmodel;
    private DateFormat df = new DateFormat();
    private Calendar myCalendar = Calendar.getInstance();
    private EditText datereelle;
    private TextView visiteDatePrevue;
    private TextView visiteNom;
    private TextView visitePrenom;
    private TextView visitead1;
    private TextView visitecp;
    private TextView visiteville;
    private TextView visitenumport;
    private TextView visitenumfixe;
    private EditText visiteCommentaire;
    private Button save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_visite);

        //Récupération des variables passées dans la valise.
        Bundle b = getIntent().getExtras();
        this.idVisite = b.getInt("idVisite");
        this.vmodel = new Modele(this);
        this.laVisite = vmodel.trouveVisite(this.idVisite);
        listeSoin = vmodel.trouveSoinsUneVisite(laVisite.getId());
        Log.d("Soins", "trouveSoinsUneVisite" + String.valueOf(listeSoin.size()));
        listView = (ListView)findViewById(R.id.lvListeSoins);

        SoinAdapter soinAdapter = new SoinAdapter(this, listeSoin);
        listView.setAdapter(soinAdapter);

        visiteDatePrevue = findViewById(R.id.visiteDatePrevue);
        visiteDatePrevue.setText(this.laVisite.getDate_prevue().toString());

        Patient patient = this.vmodel.trouvePatient(this.laVisite.getPatient());

        visiteNom = findViewById(R.id.visiteNom);
        visiteNom.setText(patient.getNom());

        visitePrenom = findViewById(R.id.visitePrenom);
        visitePrenom.setText(patient.getPrenom());

        visitead1 = findViewById(R.id.visitead1);
        visitead1.setText(patient.getAd1());

        visitecp = findViewById(R.id.visitecp);
        visitecp.setText(Integer.toString(patient.getCp()));

        visiteville = findViewById(R.id.visiteville);
        visiteville.setText(patient.getVille());

        visitenumport = findViewById(R.id.visitenumport);
        visitenumport.setText(patient.getTel_port());

        visitenumfixe = findViewById(R.id.visitenumfixe);
        visitenumfixe.setText(patient.getTel_fixe());

        save = findViewById(R.id.visitesave);
        visiteCommentaire = findViewById(R.id.visitecommentaire);
        visiteCommentaire.setText(laVisite.getCompte_rendu_infirmiere());
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                laVisite.setDate_reelle(ddatereelle);
                laVisite.setCompte_rendu_infirmiere(visiteCommentaire.getText().toString());
                for(VisiteSoin visiteSoin : listeSoin){
                    vmodel.saveVisiteSoin(visiteSoin);
                }
                vmodel.saveVisite(laVisite);
                Toast.makeText(AfficheVisite.this, "Les informations ont été sauvegardées!", Toast.LENGTH_SHORT).show();
            }
        });

        datereelle=(EditText) findViewById(R.id.visiteDateReelle);
        if(laVisite.getDate_reelle().toString().length()==0)
        {
            ddatereelle = new Date();
        }
        else
        {
            ddatereelle=laVisite.getDate_reelle();
        }
        datereelle.setText(df.format("dd/MM/yyyy", ddatereelle));

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datereelle.setText(df.format("dd/MM/yyyy", myCalendar.getTime()));
                ddatereelle=myCalendar.getTime();
            }

        };
        datereelle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AfficheVisite.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}