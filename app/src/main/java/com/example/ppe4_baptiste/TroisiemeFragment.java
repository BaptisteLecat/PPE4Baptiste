package com.example.ppe4_baptiste;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TroisiemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TroisiemeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TroisiemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TroisiemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TroisiemeFragment newInstance(String param1, String param2) {
        TroisiemeFragment fragment = new TroisiemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Récupération des textView.
        TextView txtName =  view.findViewById(R.id.txtName);
        TextView txtFirstName =  view.findViewById(R.id.txtFirstName);
        //Modification de leur value.
        txtName.setText(((MainActivity) getActivity()).getNom());
        txtFirstName.setText(((MainActivity) getActivity()).getPrenom());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_troisieme, container, false);
    }
}