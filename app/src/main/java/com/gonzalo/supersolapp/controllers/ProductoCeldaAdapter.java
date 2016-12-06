package com.gonzalo.supersolapp.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gonzalo.supersolapp.ProductoActivity;
import com.gonzalo.supersolapp.R;
import com.gonzalo.supersolapp.SupersolApp;
import com.gonzalo.supersolapp.model.Pedido;
import com.gonzalo.supersolapp.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonzalopro on 10/25/16.
 */

public class ProductoCeldaAdapter extends ArrayAdapter<Producto> {

    ProductoActivity productoActivity;


    public ProductoCeldaAdapter(ProductoActivity productoActivity, List<Producto> objects) {
        super(productoActivity, 0, objects);
        this.productoActivity = productoActivity;
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
        Button buttonAgregar = (Button) convertView.findViewById(R.id.btn_celda_producto_agregar);

        final Producto producto = getItem(position);

        assert producto != null;
        textViewProductoNombre.setText(producto.getNombre());
        textViewProductoDetalle.setText(producto.getDescripcion());
        textViewProductoPrecio.setText(producto.getPreciobase());
        Picasso.with(getContext()).load(producto.getFoto()).into(imageViewFoto);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPedido(producto, producto.getNombre(), producto.getIdProducto(), producto.getPreciobase()).show();;
            }
        });

        return convertView;
    }

    private Dialog agregarPedido(final Producto producto, String name, final String id, final String precio) {
        final TextView textViewTitle;
        final EditText editTextCantidad;

        AlertDialog.Builder builder = new AlertDialog.Builder(productoActivity);
        final LayoutInflater inflater = (LayoutInflater) productoActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_add_item, null);
        textViewTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        textViewTitle.setText(name);
        editTextCantidad = (EditText) view.findViewById(R.id.et_dialog_cantidad);
        builder.setView(view)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        addItem(producto, id, precio, editTextCantidad.getText().toString());

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    private void addItem(Producto producto, String id, String precio, String cantidad) {
        // validacion de productos que existen en el carrito de pedido

        //Log.d("AddItem", "id: " + id + " und: " + cantidad);
        ((SupersolApp) getContext().getApplicationContext()).setPedido(new Pedido(producto, id, precio, cantidad));
    }

}
