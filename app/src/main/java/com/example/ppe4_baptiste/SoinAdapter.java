package com.example.ppe4_baptiste;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class SoinAdapter extends android.widget.BaseAdapter {

    private ViewHolder holder;
    private List<VisiteSoin> listSoin;
    private LayoutInflater layoutInflater; //Cet attribut a pour mission de charger notre fichier XML de la vue pour l'item.
    private DateFormat df = new DateFormat();
    private Modele vmodel;

    public SoinAdapter(){

    }

    public SoinAdapter(Context context, List<VisiteSoin> vListSoin) {
        super();
        layoutInflater = LayoutInflater.from(context);
        listSoin = vListSoin;
        vmodel=new Modele(context);
    }

    private class ViewHolder {
        TextView textViewVisite;
        CheckBox checkRealise;
    }

    @Override
    public int getCount() {
        return listSoin.size();
    }

    @Override
    public Object getItem(int position) {
        return listSoin.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listSoin.get(position).getId_soins();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        holder.checkRealise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb =  v.findViewById(R.id.vuesoinsrealise);
                listSoin.get(position).setRealise(cb.isChecked());
                vmodel.saveVisiteSoin(listSoin.get(position));
            }
        });
        return null;
    }
}
