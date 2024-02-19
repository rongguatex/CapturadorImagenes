/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 *
 * @author RGALICIA
 */
public class GrabarLog {

    private Logger rootLoggerAdmin;
    private RollingFileAppender fileAppenderAdmin = null;
    private String nombreAdmin = "";
    private static GrabarLog grabaFile;
    private final String nameAppenderAdmin = "ap2";
    private final Logger log;

    private GrabarLog() {

        DateFormat formato = new SimpleDateFormat("yyyyMMdd");
        log = Loggs.getInstance().getLog();
        nombreAdmin = "LogServicio" + formato.format(new Date()) + ".log";
        inicializa();

    }

    private void inicializa() {

        log.debug("inicializando GrabaFile");
        rootLoggerAdmin = Logger.getLogger("log2");
        rootLoggerAdmin.setLevel(Level.INFO);
        PatternLayout layout2 = new PatternLayout();
        layout2.setConversionPattern("%d{yyyy.MM.dd HH:mm:ss.SSS}  %-5p - %m%n");

        try {

            fileAppenderAdmin = new RollingFileAppender(layout2, AdministracionCarpetas.getRUTA_LOGS() + "" + nombreAdmin);
            fileAppenderAdmin.setName(nameAppenderAdmin);
            fileAppenderAdmin.setMaxFileSize("15MB");
            fileAppenderAdmin.setMaxBackupIndex(10);
            fileAppenderAdmin.setAppend(true);

        } catch (IOException ex) {
            log.debug("Error al cargar configuracion de log4j [" + ex.getMessage() + "] [" + AdministracionCarpetas.getRUTA_LOGS() + "" + nombreAdmin + "]");
        }
        rootLoggerAdmin.addAppender(fileAppenderAdmin);

    }

    public void grabaLogFileAdministrador(String cadena) {

        try {

            RollingFileAppender ap = ((RollingFileAppender) (rootLoggerAdmin.getAppender(nameAppenderAdmin)));
            ap.setAppend(true);
            ap.activateOptions();
            rootLoggerAdmin.info(" LOG -  [" + new Date() + "] " + cadena);
            log.trace("Grabacion en archivo de log exitosa");
        } catch (Exception e) {
            log.debug("No fue posible grabar log en archivo");
        }
    }

    public static GrabarLog getInstance() {

        if (grabaFile == null) {
            grabaFile = new GrabarLog();
        }
        return grabaFile;

    }

}
