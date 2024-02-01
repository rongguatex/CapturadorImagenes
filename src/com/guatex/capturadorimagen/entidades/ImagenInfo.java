/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.entidades;

/**
 *
 * @author RGALICIA
 */
public class ImagenInfo {

    private String nombre = "";
    private String ruta = "";
    private String operador = "";
    private String fecha = "";
    private String dpi = "";

    @Override
    public String toString() {
        return "ImagenInfo{" + "nombre=" + nombre + ", ruta=" + ruta + ", operador=" + operador + ", fecha=" + fecha + ", dpi=" + dpi + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }
}
