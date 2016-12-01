package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 11/30/16.
 */

public class Promocion {

    private String idPromocion;
    private String fechaInicio;
    private String fechaFin;
    private String evento;
    private String descripcion;
    private String precioPromocion;
    private String imagen;

    public Promocion(String idPromocion, String fechaInicio, String fechaFin, String evento, String descripcion, String precioPromocion, String imagen) {
        this.idPromocion = idPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.evento = evento;
        this.descripcion = descripcion;
        this.precioPromocion = precioPromocion;
        this.imagen = imagen;
    }

    public String getIdPromocion() {
        return idPromocion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getEvento() {
        return evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecioPromocion() {
        return precioPromocion;
    }

    public String getImagen() {
        return imagen;
    }
}
