/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author RGALICIA
 */
public class ObtenerDataBase {

    private static String DIRECCIONDB;
    private static String PUERTODB;
    private static String USUARIODB;
    private static String PASSWORDDB;

    public static String getDIRECCIONDB() {
        return DIRECCIONDB;
    }

    public static String getPUERTODB() {
        return PUERTODB;
    }

    public static String getUSUARIODB() {
        return USUARIODB;
    }

    public static String getPASSWORDDB() {
        return PASSWORDDB;
    }

    public static void setDIRECCIONDB(String DIRECCIONDB) {
        ObtenerDataBase.DIRECCIONDB = DIRECCIONDB;
    }

    public static void setPUERTODB(String PUERTODB) {
        ObtenerDataBase.PUERTODB = PUERTODB;
    }

    public static void setUSUARIODB(String USUARIODB) {
        ObtenerDataBase.USUARIODB = USUARIODB;
    }

    public static void setPASSWORDDB(String PASSWORDDB) {
        ObtenerDataBase.PASSWORDDB = PASSWORDDB;
    }
    
    

    public static void obtenerConfiguracion() {
        Properties propiedades = new Properties();
        InputStream entrada = null;
        try {
            entrada = new FileInputStream(AdministracionCarpetas.getRUTA_CONFIGURACION() + "config.properties");
            propiedades.load(entrada);

            setDIRECCIONDB(decodificar(propiedades.getProperty("dirdb")));
            setPASSWORDDB(decodificar(propiedades.getProperty("pwddb")));
            setPUERTODB(decodificar(propiedades.getProperty("portdb")));
            setUSUARIODB(decodificar(propiedades.getProperty("userdb")));

            entrada.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error al obtener las propiedades : " + ex.getMessage());
        } finally {
            try {
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException ex) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error al cerrar InputStream : " + ex.getMessage());
            }
        }
    }

    private static String decodificar(String valor) {
        if (valor != null) {
            String valor_dec = "";
            for (int cont = 0; cont < valor.length(); cont += 13) {
                valor_dec += new Cifrado().DesEncriptar(valor.substring(cont, cont + 13), 10);
            }
            return valor_dec.trim();
        } else {
            return null;
        }
    }

}
