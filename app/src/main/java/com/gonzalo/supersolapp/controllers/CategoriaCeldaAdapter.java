package com.gonzalo.supersolapp.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.model.Categoria;

import java.util.List;

/**
 * Created by gonzalopro on 10/24/16.
 */

public class CategoriaCeldaAdapter extends ArrayAdapter<Categoria> {

    public CategoriaCeldaAdapter(Context context, List<Categoria> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.celda_categoria, parent, false);
        }

        TextView textViewCategoriaNombre = (TextView) convertView.findViewById(R.id.tv_celda_categoria);
        //LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_cell_categoria);

        Categoria categoria = getItem(position);

        assert categoria != null;
        textViewCategoriaNombre.setText(categoria.getDescripcion());

       /* linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return convertView;
    }
}
