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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

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

        Scene scene = new Scene(root, 1100.0, 700);

        stage.setTitle("Capturador de imágenes.");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
