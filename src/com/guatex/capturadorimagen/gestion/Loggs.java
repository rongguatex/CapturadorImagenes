/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author RGALICIA
 */
public class Loggs {

    private Logger log;

    private ConsoleAppender consoleAppender = null;

    private static Loggs grabalog = null;

    private Loggs() {
        inicializa();
    }

    private void inicializa() {
        this.log = Logger.getLogger("Class");
        this.log.setLevel(Level.toLevel("DEBUG"));
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("%d{yyyy.MM.dd HH:mm:ss.SSS}  %-5p - %m%n");

        try {
            this.consoleAppender = new ConsoleAppender((Layout) layout);
        } catch (Exception e) {
            GrabarLog.getInstance().grabaLogFileAdministrador("ERROR AL CARGAR CONFIGURACION DE log4j ");
            GrabarLog.getInstance().grabaLogFileAdministrador(e.toString());
        }
        this.log.addAppender((Appender) this.consoleAppender);
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return this.log;
    }

    public static Loggs getInstance() {
        if (grabalog == null) {
            grabalog = new Loggs();
        }
        return grabalog;
    }

}
