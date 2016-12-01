package com.gonzalo.supersolapp;

import android.app.Application;

/**
 * Created by gonzalopro on 11/30/16.
 */

public class SupersolApp extends Application {

    String idUsuario;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
