package com.example.ppe4_baptiste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.util.List;

public class AfficheListeVisite extends AppCompatActivity {

    private ListView listView;
    private List<Visite> listeVisite;
    private List<Patient> listePatient;
    private Modele vmodele;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_liste_visite);

        vmodele=new Modele(this);
        listeVisite = vmodele.listeVisite();
        listePatient = vmodele.listePatient();
        listView = (ListView)findViewById(R.id.lvListe);
        VisiteAdapter visiteAdapter = new VisiteAdapter(this, listeVisite, listePatient);
        listView.setAdapter(visiteAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                i = new Intent(getApplicationContext(), AfficheVisite.class);
                i.putExtra("idVisite", listeVisite.get(position).getId());
                startActivity(i);
            }
        });
    }
}