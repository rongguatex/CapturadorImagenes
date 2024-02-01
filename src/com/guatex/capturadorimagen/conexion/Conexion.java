/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        this.puerto = "1521";
        this.usuario = "operaciones";
        this.password = "gtxgtx01";
    }
    
    public Conexion(String direccion, String puerto, String usuario, String password) {
        this.con = null;
        this.url = "jdbc:oracle:thin:@" + direccion;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
    }
    
    public Connection getConexion() {
        if (this.url.isEmpty()) {
            try {
                System.out.println("Obteniendo conexion de datasource");
                con = DataSource.getDataSource().getConnection();
            } catch (SQLException ex) {
                ex.printStackTrace();
//                WriteLog.grabarError(Conexion.class, ex.getMessage());
            }
        } else {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(url + ":" + puerto + ":xe", usuario, password);
            } catch (SQLException ex) {
//                WriteLog.grabarError(Conexion.class, ex.getMessage());
            } catch (ClassNotFoundException ex) {
//                WriteLog.grabarError(Conexion.class, ex.getMessage());
            }
        }

        return con;
    }
    
}
