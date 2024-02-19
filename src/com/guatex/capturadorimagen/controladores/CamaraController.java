/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.controladores;

import AlertaFx.AlertaFx;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.guatex.capturadorimagen.conexion.Conexion;
import com.guatex.capturadorimagen.datos.DatosCamara;
import com.guatex.capturadorimagen.entidades.CapturasInfo;
import com.guatex.capturadorimagen.entidades.ControlImagenes;
import com.guatex.capturadorimagen.entidades.ImagenInfo;
import com.guatex.capturadorimagen.entidades.WebCamInfo;
import com.guatex.capturadorimagen.gestion.AdministracionCarpetas;
import com.guatex.capturadorimagen.gestion.GrabarLog;
import com.guatex.capturadorimagen.gestion.Parametros;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author RGALICIA
 */
public class CamaraController implements Initializable {

    @FXML
    Button btnBuscarCamara, btnCapturar, btnEliminar1, btnCerrar1, btnRotarLeft,
            btnRotarRight, btnVolverCapturar, btnGuardar, btnEliminar2, btnCerrar2;

    @FXML
    Label lbCaptura;

    @FXML
    TableView tblCapturas1, tblCapturas2;

    @FXML
    ImageView imgWebCamCapturedImage, ImgVisualizador;

    @FXML
    TitledPane tpCaptura, tpModificar;

    private String ruta = "";
    private int contador = 0;
    private BufferedImage grabbedImage;
    private AdministracionCarpetas adFolder = new AdministracionCarpetas();
    private Webcam webCam = null;
    private ObservableList<WebCamInfo> opciones;
    private List<CapturasInfo> listaCapturas = new LinkedList<>();
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    private boolean stopCamera = false;
    private String SeleccionCaptura1 = "";
    private String SeleccionCaptura2 = "";
    private boolean selecciontabla = false;
    private String nombreImagen = "";
    private ControlImagenes controlImage = new ControlImagenes();
    boolean procesoCerrar = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        controlImage.setNoDPI(Parametros.DOCUMENTO);
        controlImage.setCantidadImagenes(Parametros.CANTIDAD_IMG);

        opciones = FXCollections.observableArrayList();
        btnCapturar.setVisible(!opciones.isEmpty());
        lbCaptura.setVisible(!opciones.isEmpty());
        btnEliminar1.setVisible(!opciones.isEmpty());
        tblCapturas1.setVisible(!opciones.isEmpty());

        tpCaptura.setExpanded(true);
        tpCaptura.setDisable(false);
        tpCaptura.setCollapsible(false);
        tpModificar.setExpanded(false);
        tpModificar.setDisable(true);
        tpModificar.setCollapsible(false);

        //Listener al cambiar la propiedad Expanded de tpCaptura
        tpCaptura.expandedProperty().addListener((obsV, oldV, newV) -> {
            if (!newV) {
                Platform.runLater(() -> {
                    tpCaptura.setExpanded(false);
                    tpCaptura.setCollapsible(false);
                });
            } else {
                Platform.runLater(() -> {
                    tpCaptura.setCollapsible(false);
                });
            }
        });

        //Listener al cambiar la propiedad Expanded de tpModificar
        tpModificar.expandedProperty().addListener((obsV, oldV, newV) -> {
            if (!newV) {
                Platform.runLater(() -> {
                    tpModificar.setExpanded(false);
                    tpModificar.setCollapsible(false);
                });
            } else {
                Platform.runLater(() -> {
                    tpModificar.setCollapsible(false);
                });
            }
        });

        BuscarCamara();
        iniciaTablaCaptura1();
        iniciaTablaCaptura2();
        CameraControls();

    }

    public void BuscarCamara() {
        int webCamCounter = 0;

        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();

            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            opciones.add(webCamInfo);

            webCamCounter++;
        }

        btnBuscarCamara.setVisible(opciones.isEmpty());
        btnCapturar.setVisible(!opciones.isEmpty());
        tblCapturas1.setVisible(!opciones.isEmpty());
        btnEliminar1.setVisible(!opciones.isEmpty());
        lbCaptura.setVisible(!opciones.isEmpty());

        if (!opciones.isEmpty()) {

            GrabarLog.getInstance().grabaLogFileAdministrador("WebCam index: " + opciones.get(0).getWebCamIndex() + " - WebCam Name: " + opciones.get(0).getWebCamName());

            try {
                iniciaCamaraWeb(opciones.get(0).getWebCamIndex());
            } catch (Exception e) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Error al llamar al metodo inicaCamaraWeb: " + e.getMessage());
                disposeWebCamCamera();
                System.exit(0);
            }

        } else if (opciones.size() == 0) {

            GrabarLog.getInstance().grabaLogFileAdministrador("No hay cámara disponible");
            Platform.runLater(() -> {
                Alert alerta2 = new Alert(Alert.AlertType.INFORMATION);
                alerta2.setTitle("Cámara");
                alerta2.setContentText("No hay cámara disponible, asegurese que la cámara se encuentre conectada al equipo."
                        + "\n\nSi el problema persiste por favor contacte a soporte.");
                alerta2.showAndWait();
            });
        }

    }

    protected void iniciaCamaraWeb(final int webCamIndex) {
        try {
            Task<Void> webCamTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (webCam != null) {
                        disposeWebCamCamera();
                        System.exit(0);
                    }

                    webCam = Webcam.getWebcams().get(webCamIndex);

                    Dimension[] nonStandardResolutions = new Dimension[]{
                        WebcamResolution.PAL.getSize(),
                        WebcamResolution.QHD.getSize(),
                        new Dimension(1600, 800),
                        new Dimension(700, 300),};

                    webCam.setCustomViewSizes(nonStandardResolutions);
                    webCam.setViewSize(WebcamResolution.QHD.getSize());

                    webCam.open();
                    GrabarLog.getInstance().grabaLogFileAdministrador("webcam open ");

                    startWebCamStream();
                    return null;
                }
            };
            Thread webCamThread = new Thread(webCamTask);
            webCamThread.setDaemon(true);
            webCamThread.start();
        } catch (Exception e) {
            GrabarLog.getInstance().grabaLogFileAdministrador("iniciaCamaraWeb - Ocurrio un error al inciar la cámara web: " + e.getMessage());
            disposeWebCamCamera();
            System.exit(0);
        }
    }

    protected void startWebCamStream() {
        stopCamera = false;

        try {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    final AtomicReference<WeakReference<Image>> ref = new AtomicReference<>();

                    LocalTime horaActual = LocalTime.now();
                    int segundos = horaActual.toSecondOfDay();

                    while (!stopCamera) {
                        try {

                            if ((horaActual.toSecondOfDay() - segundos) > 2) {

                                GrabarLog.getInstance().grabaLogFileAdministrador("No se reconoce la cámara después de dos segundos.");
                                disposeWebCamCamera();

                                Platform.runLater(() -> {
                                    Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                    alerta2.setTitle("Cámara");
                                    alerta2.setContentText("La cámara ha sido desonectada, asegurese que la cámara se encuentre conectada al equipo."
                                            + "\n\nSi el problema persiste por favor contacte a soporte.");
                                    alerta2.showAndWait();

                                    Stage stage = (Stage) btnCerrar1.getScene().getWindow();
                                    stage.close();
                                });
                            }

                            if ((grabbedImage = webCam.getImage()) != null) {

                                final Image mainiamge = SwingFXUtils.toFXImage(grabbedImage, null);
                                ref.set(new WeakReference<Image>(SwingFXUtils.toFXImage(grabbedImage, null)));
                                grabbedImage.flush();

                                Platform.runLater(new Runnable() {

                                    @Override
                                    public void run() {
                                        imageProperty.set(mainiamge);
                                        Image image = ref.get().get();
                                        if (image != null) {
                                            imageProperty.set(image);
                                        }
                                    }
                                });

                                grabbedImage.flush();
                            } else {

                                GrabarLog.getInstance().grabaLogFileAdministrador("La cámara ha sido desconectada de manera imprevista.");
                                disposeWebCamCamera();

                                Platform.runLater(() -> {
                                    Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                                    alerta2.setTitle("Cámara");
                                    alerta2.setContentText("La cámara ha sido desonectada, asegurese que la cámara se encuentre conectada al equipo."
                                            + "\n\nSi el problema persiste por favor contacte a soporte.");
                                    alerta2.showAndWait();

                                    Stage stage = (Stage) btnCerrar1.getScene().getWindow();
                                    stage.close();

                                });
                            }

                        } catch (Exception e) {
                            GrabarLog.getInstance().grabaLogFileAdministrador(" -----> startWebCamStream - Ocurrio un error al obtener la imagen: " + e.getMessage());
                            disposeWebCamCamera();
                            System.exit(0);
                        }
                    }

                    return null;
                }
            };

            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            imgWebCamCapturedImage.imageProperty().bind(imageProperty);
        } catch (Exception e) {
            GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error en startWebCamStream: " + e.getMessage());
            disposeWebCamCamera();
            System.exit(0);
        }
    }

    private void iniciaTablaCaptura1() {
        tblCapturas1.getColumns().clear();
        TableColumn<CapturasInfo, String> ColNombreCaptura = new TableColumn<>("Nombre");
        ColNombreCaptura.setCellValueFactory(new PropertyValueFactory<>("capturaName"));
        ColNombreCaptura.setSortable(false);
        ColNombreCaptura.setPrefWidth(180);
        tblCapturas1.setPlaceholder(new Label("No hay capturas\n  disponibles"));
        tblCapturas1.getColumns().add(ColNombreCaptura);

        tblCapturas1.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                List<CapturasInfo> listaAux = tblCapturas1.getSelectionModel().getSelectedItems();
                if (!listaAux.isEmpty()) {
                    selecciontabla = true;
                    SeleccionCaptura1 = listaAux.get(0).getCapturaName();
                }
            }
        });

    }

    private void iniciaTablaCaptura2() {
        tblCapturas2.getColumns().clear();
        TableColumn<CapturasInfo, String> ColNombreCaptura = new TableColumn<>("Nombre");
        ColNombreCaptura.setCellValueFactory(new PropertyValueFactory<>("capturaName"));
        ColNombreCaptura.setSortable(false);
        ColNombreCaptura.setPrefWidth(160);
        tblCapturas2.setPlaceholder(new Label("No hay capturas\n  disponibles"));
        tblCapturas2.getColumns().add(ColNombreCaptura);

        tblCapturas2.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                List<CapturasInfo> listaAux = tblCapturas2.getSelectionModel().getSelectedItems();
                if (!listaAux.isEmpty()) {
                    selecciontabla = true;
                    SeleccionCaptura2 = listaAux.get(0).getCapturaName();
                }
            }
        });

    }

    private void eventoActualizaTabla() {
        Platform.runLater(() -> {
            try {
                tblCapturas1.getItems().clear();
                tblCapturas2.getItems().clear();
                tblCapturas1.getItems().addAll(listaCapturas);
                tblCapturas2.getItems().addAll(listaCapturas);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void CameraControls() {
        btnRotarLeft.setOnMouseClicked(e -> {
            RotarImagen(-90);
        });

        btnRotarRight.setOnMouseClicked(e -> {
            RotarImagen(90);
        });

        btnBuscarCamara.setOnMouseClicked(e -> {
            BuscarCamara();
            btnBuscarCamara.setVisible(opciones.isEmpty());
            btnCapturar.setVisible(!opciones.isEmpty());
            tblCapturas1.setVisible(!opciones.isEmpty());
            btnEliminar1.setVisible(!opciones.isEmpty());
            lbCaptura.setVisible(!opciones.isEmpty());
        });

        btnCapturar.setOnMouseClicked(e -> {

            contador++;

            byte[] bytes = WebcamUtils.getImageBytes(webCam, "jpg");

            nombreImagen = controlImage.getNoDPI() + "_" + (contador);
            ruta = adFolder.CreaCarpetaGuia(controlImage.getNoDPI());

            File file = null;
            if (System.getProperty("os.name").startsWith("Windows")) {
                file = new File("\\" + ruta + "\\" + controlImage.getNoDPI() + "_" + (contador) + ".jpg");
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                file = new File(ruta + "/" + controlImage.getNoDPI() + "_" + (contador) + ".jpg");
            } else {
                file = new File("\\" + ruta + "\\" + controlImage.getNoDPI() + "_" + (contador) + ".jpg");
            }

//            System.out.println("ruta... " + file.toString());
            //File file = new File("\\" + ruta + controlImage.getNoDPI() + "_" + (contador) + ".jpg");
            try {
                FileOutputStream fi = new FileOutputStream(file.toString());
                fi.write(bytes);
                fi.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //Se necesita para hacer una pausa en la secuencia de la cámara y de esta manera poder ir a la pantalla de modificar.
            stopCamera = true;

            btnCapturar.setVisible(false);
            tblCapturas1.setVisible(false);
            btnEliminar1.setVisible(false);
            lbCaptura.setVisible(false);

            tpCaptura.setCollapsible(true);
            tpCaptura.setExpanded(false);
            tpCaptura.setDisable(true);
            tpModificar.setCollapsible(true);
            tpModificar.setExpanded(true);
            tpModificar.setDisable(false);

            CargarImagen();
            imgWebCamCapturedImage.setVisible(false);
        });

        btnEliminar1.setOnMouseClicked(e -> {
            if (selecciontabla) {
                int cont = 0;
                boolean busqueda = false;
                for (CapturasInfo cc : listaCapturas) {
                    if (cc.getCapturaName().equalsIgnoreCase(SeleccionCaptura1)) {
                        busqueda = true;
                    } else {
                        if (!busqueda) {
                            cont++;
                        }
                    }
                }
                if (busqueda) {
                    File file = new File(adFolder.obtengoRuta(controlImage.getNoDPI()) + "\\" + listaCapturas.get(cont).getCapturaName() + ".jpg");

                    if (file.exists()) {
                        FileUtils.deleteQuietly(new File(file.toString()));
                    }

                    listaCapturas.remove(cont);
                    eventoActualizaTabla();
                    selecciontabla = false;
                    SeleccionCaptura1 = "";

                }
            } else {
                Platform.runLater(() -> {
                    AlertaFx Alerta = new AlertaFx("Captura", "Por favor, seleccione captura a eliminar");
                    Alerta.setTipoAlerta(AlertaFx.tipo.INFORMACION);
                    Alerta.setIcono(AlertaFx.icono.INFO);
                    Alerta.showAndWait();
                });
            }
        });

        btnVolverCapturar.setOnMouseClicked(e -> {

            //---------------------------Deshabilita los botones de tpModificar--------------------------------
            tpModificar.setCollapsible(true);
            tpModificar.setExpanded(false);
            tpModificar.setDisable(true);

            tpCaptura.setCollapsible(true);
            tpCaptura.setDisable(false);
            tpCaptura.setExpanded(true);

            //---------------------------Habilita los botones de tpCapturar--------------------------------
            btnBuscarCamara.setVisible(opciones.isEmpty());
            btnCapturar.setVisible(!opciones.isEmpty());
            tblCapturas1.setVisible(!opciones.isEmpty());
            btnEliminar1.setVisible(!opciones.isEmpty());
            lbCaptura.setVisible(!opciones.isEmpty());

            startWebCamStream();
//            BuscarCamara();
            imgWebCamCapturedImage.setVisible(true);

        });

        btnGuardar.setOnMouseClicked(e -> {
            CargarImagen();
            boolean encontre = false;
            for (CapturasInfo cap : listaCapturas) {
                if (cap.getCapturaName().equalsIgnoreCase(nombreImagen)) {
                    encontre = true;
                }
            }

            if (encontre) {
                Platform.runLater(() -> {
                    AlertaFx Alerta = new AlertaFx("Camara", "Captura ya añadida a listado");
                    Alerta.setTipoAlerta(AlertaFx.tipo.INFORMACION);
                    Alerta.setIcono(AlertaFx.icono.INFO);
                    Alerta.showAndWait();
                });
            } else {
                GrabarLog.getInstance().grabaLogFileAdministrador("Toma guardada correctamente: [" + nombreImagen + "]");
                CapturasInfo captura = new CapturasInfo();
                captura.setCapturaName(nombreImagen);

                listaCapturas.add(captura);

                eventoActualizaTabla();
                ImgVisualizador.setImage(null);
            }
        });

        btnEliminar2.setOnMouseClicked(e -> {
            if (selecciontabla) {
                int cont = 0;
                boolean busqueda = false;
                for (CapturasInfo cc : listaCapturas) {
                    if (cc.getCapturaName().equalsIgnoreCase(SeleccionCaptura2)) {
                        busqueda = true;
                    } else {
                        if (!busqueda) {
                            cont++;
                        }
                    }
                }
                if (busqueda) {
                    File file = new File(adFolder.obtengoRuta(controlImage.getNoDPI()) + "\\" + listaCapturas.get(cont).getCapturaName() + ".jpg");

                    if (file.exists()) {
                        FileUtils.deleteQuietly(new File(file.toString()));
                    }

                    listaCapturas.remove(cont);
                    eventoActualizaTabla();
                    selecciontabla = false;
                    SeleccionCaptura2 = "";
                }
            } else {
                Platform.runLater(() -> {
                    AlertaFx Alerta = new AlertaFx("Captura", "Por favor, seleccione captura a eliminar");
                    Alerta.setTipoAlerta(AlertaFx.tipo.INFORMACION);
                    Alerta.setIcono(AlertaFx.icono.INFO);
                    Alerta.showAndWait();
                });
            }
        });

        btnCerrar1.setOnMouseClicked(e -> {

            if (listaCapturas.isEmpty() || listaCapturas.size() == 0) {
                GrabarLog.getInstance().grabaLogFileAdministrador("El listado de imagenes está vacío.");
                disposeWebCamCamera();
                procesoCerrar = true;
            } else if (validacionCapturas()) {
                stopCamera = true;
                respuestaCamara("S");
                GrabarLog.getInstance().grabaLogFileAdministrador("Cerrar botón 1, validación de capturas correcta, se procede a cerrar el programa.");
            }

            if (procesoCerrar) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Se procede a cerrar el capturador de imagenes.");
                Stage stage = (Stage) btnCerrar1.getScene().getWindow();
                stage.close();
                System.exit(0);
            }

        });

        btnCerrar2.setOnMouseClicked(e -> {

            if (listaCapturas.isEmpty() || listaCapturas.size() == 0) {
                GrabarLog.getInstance().grabaLogFileAdministrador("-> El listado de imagenes está vacío.");
                disposeWebCamCamera();
                procesoCerrar = true;
            } else if (validacionCapturas()) {
                stopCamera = true;
                respuestaCamara("S");
                GrabarLog.getInstance().grabaLogFileAdministrador("Cerrar botón 2, validación de capturas correcta, se procede a cerrar el programa.");
            }

            if (procesoCerrar) {
                GrabarLog.getInstance().grabaLogFileAdministrador("-> Se procede a cerrar el capturador de imagenes.");
                Stage stage = (Stage) btnCerrar2.getScene().getWindow();
                stage.close();
                System.exit(0);
            }

        });

    }

    protected void disposeWebCamCamera() {
        stopCamera = true;
        respuestaCamara("N");
    }

    private void RotarImagen(int rotar) {
        try {
            ImgVisualizador.setImage(null);
//            System.out.println("Buscando en " + nombreImagen.substring(0, nombreImagen.indexOf("_")));
            File file = null;
            if (System.getProperty("os.name").startsWith("Windows")) {
                file = new File(adFolder.CreaCarpetaGuia(nombreImagen.substring(0, nombreImagen.indexOf("_"))) + "\\" + nombreImagen + ".jpg");
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                file = new File(adFolder.CreaCarpetaGuia(nombreImagen.substring(0, nombreImagen.indexOf("_"))) + "/" + nombreImagen + ".jpg");
            } else {
                file = new File(adFolder.CreaCarpetaGuia(nombreImagen.substring(0, nombreImagen.indexOf("_"))) + "\\" + nombreImagen + ".jpg");
            }
            BufferedImage oldImage = ImageIO.read(file);
            BufferedImage newImage = new BufferedImage(oldImage.getHeight(), oldImage.getWidth(), oldImage.getType());
            Graphics2D graphics = (Graphics2D) newImage.getGraphics();
            graphics.rotate(Math.toRadians(rotar), newImage.getWidth() / 2, newImage.getHeight() / 2);
            graphics.translate((newImage.getWidth() - oldImage.getWidth()) / 2, (newImage.getHeight() - oldImage.getHeight()) / 2);
            graphics.drawImage(oldImage, 0, 0, oldImage.getWidth(), oldImage.getHeight(), null);
            ImageIO.write(newImage, "jpg", file);
            graphics.dispose();
            oldImage.flush();
            newImage.flush();
            CargarImagen();
        } catch (Exception ex) {
            GrabarLog.getInstance().grabaLogFileAdministrador("Error al rotar imagen: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void CargarImagen() {
        Platform.runLater(() -> {
            try {
                ImgVisualizador.setImage(null);
                File ruta = null;
                if (System.getProperty("os.name").startsWith("Windows")) {
                    ruta = new File(adFolder.CreaCarpetaGuia(nombreImagen.substring(0, nombreImagen.indexOf("_"))) + "\\" + nombreImagen + ".jpg");
                } else if (System.getProperty("os.name").startsWith("Linux")) {
                    ruta = new File(adFolder.CreaCarpetaGuia(nombreImagen.substring(0, nombreImagen.indexOf("_"))) + "/" + nombreImagen + ".jpg");
                } else {
                    ruta = new File(adFolder.CreaCarpetaGuia(nombreImagen.substring(0, nombreImagen.indexOf("_"))) + "\\" + nombreImagen + ".jpg");
                }

                Image imagen = new Image(ruta.toURI().toString());
                ImgVisualizador.setImage(imagen);
            } catch (Exception ex) {
                GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error al cargar la imagen: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

    }

    private boolean validacionCapturas() {
        boolean respuesta = false;

        if (listaCapturas.size() >= controlImage.getCantidadImagenes()) {

            for (CapturasInfo captura : listaCapturas) {
                ImagenInfo control = new ImagenInfo();
                control.setDpi(controlImage.getNoDPI());
                control.setNombre(captura.getCapturaName());
                control.setRuta(ruta);
                control.setOperador(Parametros.OPERADOR);
                controlImage.getListadoCaptura().add(control);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setHeaderText("Imagenes de documento guardadas correctamente.");
            alert.setContentText("¿Desea continuar con la recolección?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {

                try (Connection conLocal = new Conexion().getConexion()) {
                    DatosCamara datos = new DatosCamara();

                    for (ImagenInfo imagen : controlImage.getListadoCaptura()) {
                        datos.guardarCapturaImagen(conLocal, imagen);
                    }

                    respuesta = true;
                    procesoCerrar = true;

                } catch (Exception e) {
                    GrabarLog.getInstance().grabaLogFileAdministrador("Ocurrio un error al presionar el boton OK al finalizar proceso.[" + e.getMessage() + "]");
                    disposeWebCamCamera();
                    procesoCerrar = true;
                }
            }

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setHeaderText("Debe de tomar al menos " + controlImage.getCantidadImagenes() + " capturas");
            alert.setContentText("Si acepta se perderán las tomas agregadas anteriormente. \n¿Está seguro que desea continuar?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                procesoCerrar = true;
                disposeWebCamCamera();
            }
        }

        return respuesta;
    }

    private void respuestaCamara(String str) {

        obtenerCarpetaRespuesta();

        try {

            String ruta = "C:\\JPOSFiles\\Camara\\" + str + ".txt";

            File file = new File(ruta);

            file.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obtenerCarpetaRespuesta() {
        try {

            String ruta = "C:\\JPOSFiles\\Camara";

            File directorio = new File(ruta);

            if (!directorio.exists()) {
                directorio.mkdirs();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
