/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import java.io.File;
import java.io.IOException;
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
    private static String RUTA_LOGS = RUTA_PRIMARIA + "\\RECOLECCION\\LOGS\\";
    private static String RUTA_CONFIGURACION = RUTA_PRIMARIA + "\\configuracion\\";
    private static String RUTA_CAMARA = RUTA_PRIMARIA + "\\Camara\\";

    public static String getRUTA_CONFIGURACION() {
        return RUTA_CONFIGURACION;
    }

    public static void setRUTA_CONFIGURACION(String RUTA_CONFIGURACION) {
        AdministracionCarpetas.RUTA_CONFIGURACION = RUTA_CONFIGURACION;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public static String getUNIDADC() {
        return UNIDADC;
    }

    public static String getRUTA_PRIMARIA() {
        return RUTA_PRIMARIA;
    }

    public static String getRUTA_IMAGENES() {
        return RUTA_IMAGENES;
    }

    public static String getRUTA_LOGS() {
        return RUTA_LOGS;
    }

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
    
    public static void crearCarpetasyArchivos() {
        definirRutaPrimaria();
        
            File rutaPrimaria = new File(RUTA_PRIMARIA);
            File directorioConfiguracion = new File(RUTA_CONFIGURACION);
            File directorioLogs = new File(RUTA_LOGS);
            File rutaCamara = new File(RUTA_CAMARA);
            File rutaImagenes = new File(RUTA_IMAGENES);
            if (!rutaPrimaria.exists()) {
                rutaPrimaria.mkdir();
            }
            if (!directorioConfiguracion.exists()) {
                directorioConfiguracion.mkdir();
            }
            if (!directorioLogs.exists()) {
                directorioLogs.mkdir();
            }
            if (!rutaCamara.exists()) {
                rutaCamara.mkdir();
            }
            if (!rutaImagenes.exists()) {
                rutaImagenes.mkdir();
            }
            
    }
    
    private static void definirRutaPrimaria() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            UNIDADC = "C:";
            RUTA_PRIMARIA = UNIDADC + "\\JPOSFiles\\";
            RUTA_LOGS = RUTA_PRIMARIA + "\\RECOLECCION\\LOGS\\";
            RUTA_CONFIGURACION = RUTA_PRIMARIA + "\\configuracion\\";
            RUTA_CAMARA = RUTA_PRIMARIA + "\\Camara\\";
            RUTA_IMAGENES = RUTA_PRIMARIA + "\\imagenes\\";
        } else if (System.getProperty("os.name").startsWith("Linux")) {
            UNIDADC = "";
            RUTA_PRIMARIA = UNIDADC + System.getProperty("user.home") + "/.JPOSFiles/";
            RUTA_LOGS = RUTA_PRIMARIA + "RECOLECCION/LOGS/";
            RUTA_CONFIGURACION = RUTA_PRIMARIA + "configuracion/";
            RUTA_IMAGENES = RUTA_PRIMARIA + "imagenes/";
            RUTA_CAMARA = RUTA_PRIMARIA + "Camara/";
        } else {
            System.out.println("Error, sistema no soportado. Iniciando valores como aplicación windows.");
            UNIDADC = "C:";
            RUTA_PRIMARIA = UNIDADC + "\\JPOSFiles\\";
            RUTA_LOGS = RUTA_PRIMARIA + "\\RECOLECCION\\LOGS\\";
            RUTA_CONFIGURACION = RUTA_PRIMARIA + "\\configuracion\\";
            RUTA_IMAGENES = RUTA_PRIMARIA + "\\imagenes\\";
            RUTA_CAMARA = RUTA_PRIMARIA + "\\Camara\\";
        }
    }
}
