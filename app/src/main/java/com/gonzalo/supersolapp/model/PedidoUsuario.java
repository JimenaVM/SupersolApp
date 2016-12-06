package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 12/6/16.
 */

public class PedidoUsuario {

    private String idPedido;
    private String fechaPedido;
    private String estado;

    public PedidoUsuario(String idPedido, String fechaPedido, String estado) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public String getEstado() {
        return estado;
    }
}
