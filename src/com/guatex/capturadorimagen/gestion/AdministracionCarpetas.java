/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author RGALICIA
 */
public class AdministracionCarpetas {

    private static String UNIDADC = "C:";
    private static String RUTA_PRIMARIA = UNIDADC + "\\JPOSFiles\\";
    private static String RUTA_IMAGENES = RUTA_PRIMARIA + "\\imagenes\\";

    private final static String IMAGENES = RUTA_IMAGENES;
    private String FECHA = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());

    public String obtengoRuta(String dato) {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return IMAGENES + "\\" + FECHA + "\\" + dato.trim();
        } else if (System.getProperty("os.name").startsWith("Linux")) {
            return IMAGENES + FECHA + "/" + dato.trim();
        } else {
            return IMAGENES + "\\" + FECHA + "\\" + dato.trim();
        }
    }

    public String CreaCarpetaOrigen() {
        String ruta = IMAGENES + FECHA;
        File f = new File(ruta);
        if (!f.exists()) {
            f.mkdirs();
        }
        System.out.println("ruta origen: " + ruta);
        return ruta;
    }

    public String CreaCarpetaGuia(String dato) {
        String ruta = "";
        if (System.getProperty("os.name").startsWith("Windows")) {
            ruta = IMAGENES + "\\" + FECHA + "\\" + dato;
        } else if (System.getProperty("os.name").startsWith("Linux")) {
            ruta = IMAGENES + FECHA + "/" + dato.trim();
        } else {
            ruta = IMAGENES + "\\" + FECHA + "\\" + dato;
        }
        File f = new File(ruta);
        if (!f.exists()) {
            f.mkdirs();
        }
        System.out.println("ruta guia: " + ruta);

        return ruta + "\\";
    }
}
