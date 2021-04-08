package com.example.ppe4_baptiste;

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

    private Async mThreadCon = null;
    private EditText login;
    private EditText pass;
    private String url;
    private String[] mesparams;

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
                /*NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_troisiemeFragment);
                ((MainActivity)getActivity()).menuConnect();*/
                mesparams=new String[3];
                mesparams[0]="1";
                mesparams[1]="https://www.btssio-carcouet.fr/ppe4/public/connect2/" + login.getText().toString() + "/" + pass.getText().toString() +"/infirmiere";
                Toast.makeText(getActivity(), mesparams[1], Toast.LENGTH_SHORT).show();
                mesparams[2]="GET";
                mThreadCon = new Async ((MainActivity)getActivity());
                mThreadCon.execute(mesparams);
            }
        });
    }
}