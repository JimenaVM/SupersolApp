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
import com.gonzalo.supersolapp.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gonzalopro on 10/25/16.
 */

public class ProductoCeldaAdapter extends ArrayAdapter<Producto> {

    public ProductoCeldaAdapter(Context context, List<Producto> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.celda_producto, parent, false);
        }

        TextView textViewProductoNombre = (TextView) convertView.findViewById(R.id.tv_celda_producto_nombre);
        TextView textViewProductoDetalle = (TextView) convertView.findViewById(R.id.tv_celda_producto_detalle);
        TextView textViewProductoPrecio = (TextView) convertView.findViewById(R.id.tv_celda_producto_precio);
        ImageView imageViewFoto = (ImageView) convertView.findViewById(R.id.iv_celda_producto_foto);

        Producto producto = getItem(position);

        assert producto != null;
        textViewProductoNombre.setText(producto.getNombre());
        textViewProductoDetalle.setText(producto.getDescripcion());
        textViewProductoPrecio.setText(producto.getCodigo());
        Picasso.with(getContext()).load(producto.getFoto()).into(imageViewFoto);

        return convertView;
    }
}
