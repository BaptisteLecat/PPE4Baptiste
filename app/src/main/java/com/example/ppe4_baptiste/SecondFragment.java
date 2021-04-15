package com.example.ppe4_baptiste;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    private EditText login;
    private EditText pass;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.bFragCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        this.login = view.findViewById(R.id.etFragId);
        this.pass = view.findViewById(R.id.etFragPassword);

        view.findViewById(R.id.bFragOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //On verifie que les informations saisies ne sont pas déjà contenu dans les sharedPreferences.
                SharedPreferences myPrefs =  getActivity().getSharedPreferences("mesvariablesglobales", 0);
                String s_login = myPrefs.getString("s_login", null);
                String s_password = myPrefs.getString("s_password", null);

                if(s_login == null && s_password == null){ //On verifie que ces éléments n'existe pas.
                    //Donc on fait appel à l'API.
                    ((MainActivity)getActivity()).testMotDePasse(login.getText().toString(), pass.getText().toString());

                }else{
                    //Si la saisie correspond avec les valeurs dans SharedPreferences.
                    if(s_login.equals(MD5.getMd5(login.getText().toString())) && s_password.equals(MD5.getMd5(pass.getText().toString()))){
                        //Récupération des informations de l'utilisateur.
                        String s_name = myPrefs.getString("s_name", null);
                        String s_firstName = myPrefs.getString("s_firstName", null);
                        if(s_name != null && s_firstName != null){
                            ((MainActivity)getActivity()).setNom(s_name);
                            ((MainActivity)getActivity()).setPrenom(s_firstName);
                        }
                        //Affichage du troisième fragment.
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_troisiemeFragment);
                        ((MainActivity)getActivity()).menuConnect();
                    }else{
                        //On fait appel à l'API.
                        ((MainActivity)getActivity()).testMotDePasse(login.getText().toString(), pass.getText().toString());
                    }
                }
            }
        });
    }
}