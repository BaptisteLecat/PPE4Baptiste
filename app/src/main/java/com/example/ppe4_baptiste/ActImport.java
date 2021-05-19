package com.example.ppe4_baptiste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.reflect.Type;

public class ActImport extends AppCompatActivity {

    private Async mThreadCon = null;
    private boolean permissionOverlay;
    private String[] mesparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_import);

        //Récupération des variables passées dans la valise.
        Bundle b = getIntent().getExtras();
        this.permissionOverlay = b.getBoolean("permissionOverlay");
        Toast.makeText(getApplicationContext(),	"permissionOverlay : " + permissionOverlay,Toast.LENGTH_SHORT).show();

        findViewById(R.id.actImport_btnImport).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appelAPILoadVisite();
            }
        });
    }

    private void appelAPILoadPatient(int idPatient){
        mesparams=new String[3];
        mesparams[0]="2";
        mesparams[1]="https://www.btssio-carcouet.fr/ppe4/public/personne/" + idPatient;
        mesparams[2]="GET";
        mThreadCon = new Async (ActImport.this);
        mThreadCon.execute(mesparams);
    }

    public void alertmsg(String title, String msg) {
        if(this.permissionOverlay) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage(msg)
                    .setTitle(title);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });

            AlertDialog dialog = builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.
                    TYPE_APPLICATION_OVERLAY);
            dialog.show();
        }else{
            Toast.makeText(getApplicationContext(), title + "\n" + msg, Toast.LENGTH_LONG).show();
        }
    }

    public void appelAPILoadVisite(){
        mesparams=new String[3];
        mesparams[0]="2";
        mesparams[1]="https://www.btssio-carcouet.fr/ppe4/public/mesvisites/3";
        mesparams[2]="GET";
        mThreadCon = new Async (ActImport.this);
        mThreadCon.execute(mesparams);
    }

    public void retourImportVisite(StringBuilder sb)
    {
        ArrayList<Integer> lesPatients = new ArrayList<Integer>();
        //alertmsg("retour Connexion", sb.toString());
        try {
            Modele vmodel = new Modele(this);
            JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            ArrayList<Visite> listeVisite = new ArrayList<Visite>();
            for (JsonElement obj : varray) {
                Visite visite = gson.fromJson(obj.getAsJsonObject(), Visite.class);
                visite.setCompte_rendu_infirmiere("");
                visite.setDate_reelle(visite.getDate_prevue());
                listeVisite.add(visite);
                //Ajout du patient si il n'est pas déjà présent dans la liste.
                if(!this.idAlreadyExist(visite.getPatient(), lesPatients)){
                    lesPatients.add(visite.getPatient());
                }
            }
            vmodel.deleteVisite();
            vmodel.addVisite(listeVisite);
            alertmsg("Retour", "Vos informations ont bien été importées avec succès !");
        }
        catch (Exception e) {
            alertmsg("Erreur retour import", e.getMessage());
        }
    }

    public void retourImportPatient(StringBuilder sb)
    {
        //alertmsg("retour Connexion", sb.toString());
        try {
            Modele vmodel = new Modele(this);
            JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).serializeNulls().create();
            ArrayList<Patient> listePatient = new ArrayList<Patient>();
            for (JsonElement obj : varray) {
                Patient patient = gson.fromJson(obj.getAsJsonObject(), Patient.class);
            }
            vmodel.deletePatient();
            vmodel.addPatient(listePatient);
            alertmsg("Retour", "Vos informations ont bien été importées avec succès !");
        }catch (JsonParseException e) {
            Log.d("Patient", "erreur json" + e.getMessage());
        }
        catch (Exception e) {
            alertmsg("Erreur retour import", e.getMessage());
        }
    }

    public void appelAPILoadSoinsVisite(Visite visite){
        mesparams=new String[3];
        mesparams[0]="2";
        mesparams[1]="https://www.btssio-carcouet.fr/ppe4/public/visitesoins/".concat(Integer.toString(visite.getId()));
        mesparams[2]="GET";
        mThreadCon = new Async (ActImport.this);
        mThreadCon.execute(mesparams);
    }

    public void retourImportSoinsVisite(StringBuilder sb)
    {
        //alertmsg("retour Connexion", sb.toString());
        try {
            Modele vmodel = new Modele(this);
            JsonElement json = new JsonParser().parse(sb.toString());
            JsonArray varray = json.getAsJsonArray();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd HH:mm:ss").serializeNulls().create();
            ArrayList<VisiteSoin> listeSoinsVisite = new ArrayList<VisiteSoin>();
            for (JsonElement obj : varray) {
                VisiteSoin visiteSoin = gson.fromJson(obj.getAsJsonObject(), VisiteSoin.class);
                if(!this.SoinsAlreadyExist(visiteSoin.getId_soins(), vmodel.listeSoin())){
                    vmodel.
                }
            }
            vmodel.deleteVisiteSoin();
            vmodel.addVisiteSoin(listeSoinsVisite);
            alertmsg("Retour", "Vos informations ont bien été importées avec succès !");
        }catch (JsonParseException e) {
            Log.d("VisiteSoin", "erreur json" + e.getMessage());
        }
        catch (Exception e) {
            alertmsg("Erreur retour import", e.getMessage());
        }
    }

    private boolean idAlreadyExist(int idToFind, ArrayList<Integer> listId){
        boolean finded = false;
        for (Integer idElement : listId){
            if(idElement.equals(idToFind)){
                finded = true;
                break;
            }
        }

        return finded;
    }

    private boolean SoinsAlreadyExist(int idSoinsToFind, ArrayList<Soin> listSoins){
        boolean finded = false;
        for (Soin soinsElement : listSoins){
            if(idSoinsToFind == soinsElement.getId()){
                finded = true;
                break;
            }
        }

        return finded;
    }

    private class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
    }

}