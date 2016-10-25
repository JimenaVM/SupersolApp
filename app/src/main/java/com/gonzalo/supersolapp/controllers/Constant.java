package com.gonzalo.supersolapp.controllers;

/**
 * Created by gonzalopro on 10/24/16.
 */

public class Constant {

    // HTTPS REQUEST
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    // WEB SERVICES REST
    public static final String _URL_CATEGORIA = "http://192.168.1.42:80/supersol/Servicio/getCategoria.php";
    public static final String _URL_PRODUCTO = "http://192.168.1.42:80/supersol/Servicio/getProducto.php";
    public static final String _URL_AUTENTIFICACION = "http://192.168.1.42:80/supersol/Servicio/autentificacion.php";
}
