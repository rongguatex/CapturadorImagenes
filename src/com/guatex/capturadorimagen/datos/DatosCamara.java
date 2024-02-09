/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.datos;

import com.guatex.capturadorimagen.entidades.ImagenInfo;
import com.guatex.capturadorimagen.gestion.GrabarLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author RGALICIA
 */
public class DatosCamara {

    public void guardarCapturaImagen(Connection con, ImagenInfo captura) {
        String query = "INSERT INTO CONTROL_DOCS_IMAGES(NO_DPI, RUTA, NOMBRE_IMG, FECHA, OPERADOR) "
                + " VALUES (?,?, ?, SYSDATE, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, captura.getDpi());
            ps.setString(2, captura.getRuta());
            ps.setString(3, captura.getNombre());
            ps.setString(4, captura.getOperador());
            ps.executeUpdate();
        } catch (SQLException ex) {
            GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error al insertar en la db: \n [" + ex.getMessage() + "]");
        }

    }

}
