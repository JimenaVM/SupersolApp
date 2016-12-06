package com.gonzalo.supersolapp.controllers;

/**
 * Created by gonzalopro on 10/24/16.
 */

public class Constant {

    public static final String IP = "192.168.43.74:80";

    // HTTPS REQUEST
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    // WEB SERVICES REST
    public static final String _URL_CATEGORIA = "http://"+ IP +"/supersol/Servicio/getCategoria.php";
    public static final String _URL_PRODUCTO = "http://"+ IP +"/supersol/Servicio/getProducto.php";
    public static final String _URL_AUTENTIFICACION = "http://"+ IP +"/supersol/Servicio/autentificacion.php";
    public static final String _URL_CREDITO = "http://"+ IP +"/supersol/Servicio/getCredito.php";
    public static final String _URL_PROMOCION = "http://"+ IP +"/supersol/Servicio/getPromocion.php";
    public static final String _URL_PERFIL = "http://"+ IP +"/supersol/Servicio/getPerfil.php";
    public static final String _URL_PEDIDO = "http://"+ IP +"/supersol/Servicio/getPedido.php";
    public static final String _URL_PEDIDO_USUARIO = "http://"+ IP +"/supersol/Servicio/getPedidoUsuario.php";
    public static final String _URL_PEDIDO_DETALLE = "http://"+ IP +"/supersol/Servicio/getDetallePedido.php";
}
