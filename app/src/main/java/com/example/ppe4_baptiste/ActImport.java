package com.example.ppe4_baptiste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
                mesparams=new String[3];
                mesparams[0]="2";
                mesparams[1]="https://www.btssio-carcouet.fr/ppe4/public/mesvisites/3";
                mesparams[2]="GET";
                mThreadCon = new Async (ActImport.this);
                mThreadCon.execute(mesparams);
            }
        });
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

    public void retourImport(StringBuilder string){
        alertmsg( "StringBuilder : ",  string.toString());
    }
}