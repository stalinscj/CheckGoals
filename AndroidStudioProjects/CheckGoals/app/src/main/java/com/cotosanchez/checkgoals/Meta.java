package com.cotosanchez.checkgoals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Stalincj on 04/07/2016.
 */
public class Meta {
    private int idMeta;
    private int idUsuario;
    private String nombre;
    private int notaAcumulada;
    private int notaEvaluada;
    private int notaMinima;
    private boolean lograda;
    private boolean finalizada;
    private String fecha;

    public Meta(int idMeta, int idUsuario, String nombre, int notaAcumulada, int notaEvaluada, int notaMinima , boolean lograda, boolean finalizada, String fecha) {
        this.idMeta = idMeta;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.notaAcumulada = notaAcumulada;
        this.notaEvaluada = notaEvaluada;
        this.notaMinima = notaMinima;
        this.lograda = lograda;
        this.finalizada = finalizada;
        this.fecha = fecha;
    }

    public Meta(JSONObject metaJSON) {
        try {
            this.idMeta = metaJSON.getInt("id_meta");
            this.idUsuario = metaJSON.getInt("id_usuario");
            this.nombre = metaJSON.getString("nombre");
            this.notaAcumulada = metaJSON.getInt("nota_acumulada");
            this.notaEvaluada = metaJSON.getInt("nota_evaluada");
            this.notaMinima = metaJSON.getInt("nota_minima");
            this.fecha = metaJSON.getString("fecha");

            if(metaJSON.getInt("lograda")==0)
                this.lograda = false;
            else
                this.lograda = true;

            if(metaJSON.getInt("finalizada")==0)
                this.finalizada = false;
            else
                this.finalizada = true;

        } catch (JSONException e) {
            this.idMeta = 0;
            this.idUsuario = 0;
            this.nombre = "S/N";
            this.notaAcumulada = 0;
            this.notaEvaluada = 0;
            this.notaMinima = 0;
            this.lograda = false;
            this.finalizada = false;
            this.fecha = "dd-mm-aaaa";
            e.printStackTrace();
        }

    }

    public int getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNotaAcumulada() {
        return notaAcumulada;
    }

    public void setNotaAcumulada(int notaAcumulada) {
        this.notaAcumulada = notaAcumulada;
    }

    public int getNotaMinima() {
        return notaMinima;
    }

    public void setNotaMinima(int notaMinima) {
        this.notaMinima = notaMinima;
    }

    public int getNotaEvaluada() {
        return notaEvaluada;
    }

    public void setNotaEvaluada(int notaEvaluada) {
        this.notaEvaluada = notaEvaluada;
    }

    public boolean isLograda() {
        return lograda;
    }

    public void setLograda(boolean lograda) {
        this.lograda = lograda;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
