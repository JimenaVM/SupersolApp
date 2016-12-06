package com.gonzalo.supersolapp.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.model.PedidoUsuario;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gonzalopro on 12/6/16.
 */
public class PedidoUsuarioCeldaAdapter extends ArrayAdapter<PedidoUsuario> {

    public PedidoUsuarioCeldaAdapter(Context context, List<PedidoUsuario> pedidos) {
        super(context,0,pedidos);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.celda_pedido_usuario, parent, false);
        }

        TextView textViewProductoNombre = (TextView) convertView.findViewById(R.id.tv_celda_pedido_usuario_nombre);
        TextView textViewProductoDetalle = (TextView) convertView.findViewById(R.id.tv_celda_pedido_usuario_detalle);

        final PedidoUsuario pedido = getItem(position);

        assert pedido != null;
        textViewProductoNombre.setText(pedido.getFechaPedido());
        switch (pedido.getEstado()) {
            case "0":
                textViewProductoDetalle.setText("PENDIENTE");
                break;

            case "1":
                textViewProductoDetalle.setText("RECIBIDO");
                break;

        }

        return convertView;
    }
}
