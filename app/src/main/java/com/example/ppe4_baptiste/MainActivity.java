package com.example.ppe4_baptiste;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity {

    private Menu lemenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        lemenu=menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.menu_connect:
                boolean isFirstFragment=(Navigation.findNavController(this,R.id.nav_host_fragment).getCurrentDestination().getId()==R.id.FirstFragment);
                if(permissionOK){
                    if(isFirstFragment){
                        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_FirstFragment_to_SecondFragment);
                    }
                }else{
                    checkPermissions();
                }
                break;
            case R.id.menu_list:
                Toast.makeText(getApplicationContext(), "clic sur menu list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_import:
                Toast.makeText(getApplicationContext(), "clic sur menu import", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_export:
                Toast.makeText(getApplicationContext(), "clic sur menu export", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_deconnect:
                boolean isThirdFragment=(Navigation.findNavController(this,R.id.nav_host_fragment).getCurrentDestination().getId()==R.id.troisiemeFragment);
                Toast.makeText(getApplicationContext(), "clic sur menu deconnect", Toast.LENGTH_SHORT).show();
                if(isThirdFragment){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_troisiemeFragment_to_FirstFragment);
                    menuDeconnect();
                }
                break;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "clic sur menu settings", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //Permet de rendre visible tout les items du menu sauf connexion.
    public void menuConnect(){
        this.lemenu.findItem(R.id.menu_list).setVisible(true);
        this.lemenu.findItem(R.id.menu_import).setVisible(true);
        this.lemenu.findItem(R.id.menu_export).setVisible(true);
        this.lemenu.findItem(R.id.menu_deconnect).setVisible(true);
        this.lemenu.findItem(R.id.action_settings).setVisible(true);
        //Visible false sur le menu connect.
        this.lemenu.findItem(R.id.menu_connect).setVisible(false);
    }

    //Permet de rendre visible tout les items du menu sauf deconnexion.
    public void menuDeconnect(){
        this.lemenu.findItem(R.id.menu_list).setVisible(false);
        this.lemenu.findItem(R.id.menu_import).setVisible(false);
        this.lemenu.findItem(R.id.menu_export).setVisible(false);
        this.lemenu.findItem(R.id.menu_deconnect).setVisible(false);
        this.lemenu.findItem(R.id.action_settings).setVisible(false);
        //Visible true sur le menu deconnect.
        this.lemenu.findItem(R.id.menu_connect).setVisible(true);
    }

    // Demande et vérification des permissions
    //private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.READ_CONTACTS};
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET,Manifest.permission.READ_CONTACTS,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int MULTIPLE_PERMISSIONS = 10;
    private List<String> listPermissionsNeeded;
    private boolean permissionOverlayAsked=false;
    private boolean permissionOverlay=false;


    @Override
    public void onStart() {
        super.onStart();
        super.onResume();
        if(!permissionOverlayAsked) {
            checkPermissionAlert();
        }
        checkPermissions();
    }
    private boolean permissionOK=false;

    private  void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = checkSelfPermission(p);
                if (result != PackageManager.PERMISSION_GRANTED) { //Test si la permission est déjà mise.
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            } else {
                // Toutes les permissions sont ok
                permissionOK = true;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    int posi=0;
                    for (String per : permissionsList) {
                        if(grantResults[posi] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;
                        }
                        posi++;
                    }
                    // Show permissionsDenied
                    if(permissionsDenied.length()>0) {
                        Toast.makeText(getApplicationContext(),    "Nous ne pouvons pas continuer l'application car ces permissions sont nécéssaires : \n"+permissionsDenied,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        permissionOK=true;
                    }
                }
                return;
            }
        }
    }

    // Alert Message

    public void alertmsg(String title, String msg) {
        if(permissionOverlay) {
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

    // Autorisation des alert Message
    public static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
    public void checkPermissionAlert() {
        permissionOverlayAsked=true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
            else {
                permissionOverlay=true;

            }
        }
        else
        {
            permissionOverlay=true;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // on regarde quelle Activity a répondu
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        alertmsg("Permission ALERT","Permission OK");
                        permissionOverlay=true;
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Pbs demande de permissions "
                                , Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }



}