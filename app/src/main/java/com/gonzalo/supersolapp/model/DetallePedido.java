package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 12/6/16.
 */
public class DetallePedido {

    private String foto;
    private String nombre;
    private String cantidad;
    private String preciobase;

    public DetallePedido(String foto, String nombre, String cantidad, String preciobase) {
        this.foto = foto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.preciobase = preciobase;
    }

    public String getFoto() {
        return foto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getPreciobase() {
        return preciobase;
    }
}
