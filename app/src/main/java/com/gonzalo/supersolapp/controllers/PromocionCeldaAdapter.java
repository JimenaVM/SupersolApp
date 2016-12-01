package com.gonzalo.supersolapp.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.model.Promocion;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gonzalopro on 12/1/16.
 */
public class PromocionCeldaAdapter extends ArrayAdapter<Promocion> {

    public PromocionCeldaAdapter(Context context, List<Promocion> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.celda_promocion, parent, false);
        }

        TextView dateStart = (TextView) convertView.findViewById(R.id.tv_celda_promocion_start);
        TextView dateEnd = (TextView) convertView.findViewById(R.id.tv_celda_promocion_end);
        TextView event = (TextView) convertView.findViewById(R.id.tv_celda_promocion_event);
        TextView descripcion = (TextView) convertView.findViewById(R.id.tv_celda_promocion_description);
        TextView precio = (TextView) convertView.findViewById(R.id.tv_celda_promocion_precio);
        ImageView logo = (ImageView) convertView.findViewById(R.id.iv_celda_promocion_logo);

        Promocion promocion = getItem(position);

        assert promocion != null;
        dateStart.setText("Inicio: " + promocion.getFechaInicio());
        dateEnd.setText("Fin: " +promocion.getFechaFin());
        event.setText(promocion.getEvento());
        descripcion.setText(promocion.getDescripcion());
        precio.setText(promocion.getPrecioPromocion());
        Picasso.with(getContext()).load(promocion.getImagen()).into(logo);

        return convertView;
    }
}
