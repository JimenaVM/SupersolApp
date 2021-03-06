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
import com.gonzalo.supersolapp.model.Pedido;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gonzalopro on 12/6/16.
 */

public class PedidoCeldaAdapter extends ArrayAdapter<Pedido> {

    public PedidoCeldaAdapter(Context context, List<Pedido> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.celda_pedido, parent, false);
        }

        TextView textViewProductoNombre = (TextView) convertView.findViewById(R.id.tv_celda_pedido_nombre);
        TextView textViewProductoDetalle = (TextView) convertView.findViewById(R.id.tv_celda_pedido_detalle);
        TextView textViewProductoPrecio = (TextView) convertView.findViewById(R.id.tv_celda_pedido_precio);
        ImageView imageViewFoto = (ImageView) convertView.findViewById(R.id.iv_celda_pedido_foto);

        final Pedido pedido = getItem(position);

        assert pedido != null;
        textViewProductoNombre.setText(pedido.getCantidad() + " unidades.");
        textViewProductoDetalle.setText(pedido.getProducto().getNombre());
        textViewProductoPrecio.setText(pedido.getPrecio());
        Picasso.with(getContext()).load(pedido.getProducto().getFoto()).into(imageViewFoto);

        return convertView;
    }
}
