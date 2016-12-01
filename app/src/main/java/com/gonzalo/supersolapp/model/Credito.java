package com.gonzalo.supersolapp.model;

/**
 * Created by gonzalopro on 11/30/16.
 */

public class Credito {

    private String idVenta;
    private String fecha;
    private String total;
    private String codigoControl;

    public Credito(String idVenta, String fecha, String total, String codigoControl) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
        this.codigoControl = codigoControl;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTotal() {
        return total;
    }

    public String getCodigoControl() {
        return codigoControl;
    }
}
