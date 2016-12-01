package com.gonzalo.supersolapp.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.model.Credito;

import java.util.List;

/**
 * Created by gonzalopro on 11/30/16.
 */
public class CreditoCeldaAdapter extends ArrayAdapter<Credito> {

    public CreditoCeldaAdapter(Context context, List<Credito> creditos) {
        super(context, 0,creditos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.celda_credito, parent, false);
        }

        TextView date = (TextView) convertView.findViewById(R.id.tv_celda_credito_fecha);
        TextView code = (TextView) convertView.findViewById(R.id.tv_celda_credito_codigo);
        TextView total = (TextView) convertView.findViewById(R.id.tv_celda_credito_total);

        Credito credito = getItem(position);

        assert credito != null;
        date.setText(credito.getFecha());
        code.setText(credito.getCodigoControl());
        total.setText(credito.getTotal() + " Bs.");

        return convertView;
    }
}
