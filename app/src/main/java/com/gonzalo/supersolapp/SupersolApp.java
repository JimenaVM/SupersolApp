package com.gonzalo.supersolapp;

import android.app.Application;

import com.gonzalo.supersolapp.model.Pedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonzalopro on 11/30/16.
 */

public class SupersolApp extends Application {

    String idUsuario;
    Pedido pedido;
    List<Pedido> pedidos = new ArrayList<>();

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
        this.pedidos.add(pedido);
    }

    public void clearPedido() {
        pedidos.clear();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
