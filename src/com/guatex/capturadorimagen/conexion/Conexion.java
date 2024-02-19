/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.conexion;

import com.guatex.capturadorimagen.gestion.GrabarLog;
import com.guatex.capturadorimagen.gestion.ObtenerDataBase;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author RGALICIA
 */
public class Conexion {

    Connection con;
    private final String url;
    private final String puerto;
    private final String usuario;
    private final String password;

    public Conexion() {
        con = null;
        this.url = "";
        this.puerto = ObtenerDataBase.getPUERTODB();
        this.usuario = ObtenerDataBase.getUSUARIODB();
        this.password = ObtenerDataBase.getPASSWORDDB();
    }

    public Conexion(String direccion, String puerto, String usuario, String password) {
        this.con = null;
        this.url = "jdbc:oracle:thin:@" + direccion;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Se crea conexión a db por medio de datasource o bien por DriverManager.
     * 
     * @return Connection type
     */
    public Connection getConexion() {
        if (this.url.isEmpty()) {
            try {
                System.out.println("Obteniendo conexion de datasource");
                con = DataSource.getDataSource().getConnection();
            } catch (SQLException ex) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Error: " + ex.getMessage());
            }
        } else {
            try {
                System.out.println("getconnection");
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(url + ":" + puerto + ":xe", usuario, password);
            } catch (SQLException ex) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error en getConexion - SQLException: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error en getConexion - ClassNotFoundException: " + ex.getMessage());
            }
        }

        return con;
    }

}
