package com.example.ppe4_baptiste;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Menu lemenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                if(isFirstFragment){
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_FirstFragment_to_SecondFragment);
                }else {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_SecondFragment_to_FirstFragment);
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
}