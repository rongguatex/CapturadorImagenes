/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.datos;

import com.guatex.capturadorimagen.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 *
 * @author RGALICIA
 */
public class ObtenerPrmtros {

    public String dbTest() {

        String respuesta = null;

        System.out.println("ingresa a dbtest");
        
        try (Connection con = new Conexion().getConexion()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT CODIGOVERSION FROM PRMTRO"); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    respuesta = quitaNulo(rs.getString("CODIGOVERSION"));
                }
            }
        } catch (Exception e) {
            System.out.println("error en la conexión a la db. => [" + e.getMessage() + "]");
            System.out.println("" + e.getCause());
        }

        return respuesta;
    }

    public String quitaNulo(String var) {
        return var == null ? "" : var;
    }

}
