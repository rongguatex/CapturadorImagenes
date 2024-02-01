/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import com.guatex.capturadorimagen.conexion.Conexion;
import com.guatex.capturadorimagen.controladores.CamaraController;
import com.guatex.capturadorimagen.datos.ObtenerPrmtros;
import com.guatex.capturadorimagen.entidades.ControlImagenes;
import java.io.File;
import java.sql.Connection;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author RGALICIA
 */
public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Parameters params = getParameters();

        ControlImagenes controlImage = new ControlImagenes();
        controlImage.setTipoImagen("DPI");

        Parametros.DOCUMENTO = params.getNamed().get("documento");
        Parametros.CANTIDAD_IMG = Integer.parseInt(params.getNamed().get("cantidad"));
        Parametros.OPERADOR = params.getNamed().get("operador");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/guatex/capturadorimagen/formularios/Camara.fxml"));
        Parent root = loader.load();

        stage.setTitle("Capturador de imágenes.");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root, 1100.0, 700));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        if (args.length <= 1) { //si no hay parámetros      
//            System.out.println("Faltan parámetros");
//            System.exit(0);
//        } else {
//            System.out.println("args.length: " + args.length);
        Application.launch(args);
//            System.exit(0);
//        }

    }

}
