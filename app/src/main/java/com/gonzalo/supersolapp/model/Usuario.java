package com.gonzalo.supersolapp.model;

/**
 * Created by daniela on 1/12/2016.
 */

public class Usuario {

    private String ci;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String login;
    private String telefono;
    private String estadoCredito;

    public Usuario(String ci, String nombre, String apellidoPaterno, String apellidoMaterno, String login, String telefono, String estadoCredito) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.login = login;
        this.telefono = telefono;
        this.estadoCredito = estadoCredito;
    }

    public String getCi() {
        return ci;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getLogin() {
        return login;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEstadoCredito() {
        return estadoCredito;
    }
}
