package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 12/6/16.
 */

public class Pedido {

    private Producto producto;
    private String id;
    private String precio;
    private String cantidad;


    public Pedido(Producto producto, String id, String precio, String cantidad) {
        this.producto = producto;
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public String getId() {
        return id;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }
}
