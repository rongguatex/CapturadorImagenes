/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.entidades;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RGALICIA
 */
public class ControlImagenes {

    private String NoDPI = "";
    private String tipoImagen = "";
    private int cantidadImagenes = 0;

    List<ImagenInfo> listadoCaptura = new LinkedList<ImagenInfo>();

    @Override
    public String toString() {
        return "ControlImagenes{" + "NoDPI=" + NoDPI + ", tipoImagen=" + tipoImagen + ", cantidadImagenes=" + cantidadImagenes + ", listadoCaptura=" + listadoCaptura + '}';
    }

    public String getNoDPI() {
        return NoDPI;
    }

    public void setNoDPI(String NoDPI) {
        this.NoDPI = NoDPI;
    }

    public String getTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(String tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public int getCantidadImagenes() {
        return cantidadImagenes;
    }

    public void setCantidadImagenes(int cantidadImagenes) {
        this.cantidadImagenes = cantidadImagenes;
    }

    public List<ImagenInfo> getListadoCaptura() {
        return listadoCaptura;
    }

    public void setListadoCaptura(List<ImagenInfo> listadoCaptura) {
        this.listadoCaptura = listadoCaptura;
    }
}
