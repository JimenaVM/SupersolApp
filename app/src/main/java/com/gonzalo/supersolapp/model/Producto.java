package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 10/25/16.
 */

public class Producto {

    private String idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String foto;
    private String estado;
    private String idUnidadMedida;
    private String idCategoria;

    public Producto(String idProducto, String codigo, String nombre, String descripcion, String foto, String estado, String idUnidadMedida, String idCategoria) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.estado = estado;
        this.idUnidadMedida = idUnidadMedida;
        this.idCategoria = idCategoria;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(String idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
}
