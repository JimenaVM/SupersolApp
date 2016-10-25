package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 10/24/16.
 */

public class Categoria {

    private String idCategoria;
    private String descripcion;
    private String Impuesto_idImpuesto;

    public Categoria(String idCategoria, String descripcion, String impuesto_idImpuesto) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.Impuesto_idImpuesto = impuesto_idImpuesto;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImpuesto_idImpuesto() {
        return Impuesto_idImpuesto;
    }

    public void setImpuesto_idImpuesto(String impuesto_idImpuesto) {
        Impuesto_idImpuesto = impuesto_idImpuesto;
    }
}
