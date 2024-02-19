/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

import com.guatex.capturadorimagen.entidades.ControlImagenes;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Capturador de Imagenes v1 - 09/02/2024
 * 
 * @param Los parametros obtenidos se obtienen desde la aplicación que realiza el llamado a esta aplicación.
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

        AdministracionCarpetas.crearCarpetasyArchivos();
        ObtenerDataBase.obtenerConfiguracion();

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

        //Permite que el stage se coloque frente por 3 segundos y luego se desbloquee para que el usuario 
        //vea siempre vea esta pantalla sobre cualquier otra pestaña.
        stage.setAlwaysOnTop(true);

        stage.setOnShown(ev -> {
            PauseTransition pt = new PauseTransition(Duration.seconds(3));
            pt.setOnFinished(e -> {
                System.out.println("oki pasaron los segundos");
                stage.setAlwaysOnTop(false);
            });

            pt.play();
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
