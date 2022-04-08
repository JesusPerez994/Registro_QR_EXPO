import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import data.Preferencias;
import funciones_simples_fx.Filtro;
import identidadesFX.VisitanteFX;
import interfaz.tabla.TablaVisitantes;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import json.JSONData;
import messages.Mensajes;
import misql.Conexion;
import org.json.JSONException;
import printer.Impresora;

import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    private Webcam webcam = null;

    private boolean isReading=false;
    private boolean isEditingDB=false;
    @FXML
    private AnchorPane readCodePane;

    @FXML
    private Pane cameraPane;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField tfDBRoute, tfDBUser, tfSearch;

    @FXML
    private PasswordField pfDBPassword;

    @FXML
    private VBox vBoxTabla;

    @FXML
    private ChoiceBox<String> cbCamera,cbPrinter;

    @FXML
    private Button btnEditDB,btnTestConnection,btnReadQR;

    @FXML
    private TableColumn colEmpresa,colContacto,colEstado,colCiudad;

    @FXML
    private ComboBox<?> cbClasificacion;

    boolean stopWebcam=true;
    Node nodo;

    TablaVisitantes tabla = new TablaVisitantes();
    ObservableList datosTabla = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nodo=this.tfDBRoute;
        this.vBoxTabla.getChildren().add(tabla);

        //recargarVisitantes();
        /*tfEmpresa.setText("AFAMO");
        tfContacto.setText("JESUS AUREO PEREZ ROSAS");
        tfTelefono.setText("3921135490");
        tfEmail.setText("jesues994@gmail.com");*/
        tfDBRoute.setText(Preferencias.get(Preferencias.Key.DB_ROUTE));
        tfDBUser.setText(Preferencias.get(Preferencias.Key.DB_USER));
        pfDBPassword.setText(Preferencias.get(Preferencias.Key.DB_PASSWORD));
        Webcam.getWebcams().forEach(cam -> this.cbCamera.getItems().add(cam.getName()));
        cbCamera.getSelectionModel().select(0);
        this.cbPrinter.getItems().addAll(Impresora.getPrintServicesAsStrings());
        cbPrinter.getSelectionModel().select(Preferencias.get(Preferencias.Key.IMPRESORA));
        cbPrinter.setOnAction(e->Preferencias.put(Preferencias.Key.IMPRESORA,cbPrinter.getValue()));
        buildFiltros();
    }
    private void buildFiltros(){
        List<Function<VisitanteFX,Object>>funciones = new ArrayList<>();
        funciones.add(VisitanteFX::getNombre);
        funciones.add(VisitanteFX::empresaProperty);
        funciones.add(VisitanteFX::getTelefono);
        funciones.add(VisitanteFX::getMail);
        funciones.add(VisitanteFX::ciudadProperty);
        funciones.add(VisitanteFX::estadoProperty);
        funciones.add(VisitanteFX::cpProperty);
        funciones.add(VisitanteFX::aliasProperty);
        List<ObjectProperty<Predicate<VisitanteFX>>>filtros=new ArrayList<>();
        filtros.add(Filtro.contains(tfSearch,funciones));
        Filtro.aplicarFiltros(this.tabla,datosTabla,filtros);
    }
    private void initWebcam() {
        final SwingNode swingNode = new SwingNode();
        cameraPane.getChildren().add(swingNode);
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = getSelectedWebcam();
        webcam.close();
        webcam.setViewSize(size);
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(size);
        //webcamPanel.setFPSDisplayed(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                swingNode.setContent(webcamPanel);
            }
        });
    }
    private Webcam getSelectedWebcam(){
        return Webcam.getWebcams().get(0);//0 is default webcam
    }
    public void readQR() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException e) {
                //No result...
            }
            if (result != null) {
                //this.textArea.setText(result.getText());
                webcam.close();
                stopWebcam=true;
                final String result2=result.getText();
                Runnable task = () -> {
                    Platform.runLater(() -> {
                        qrRead(result2);
                    });
                };
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
                return;
            }
            if (stopWebcam){
                webcam.close();
                return;
            }
        } while (true);
    }
    void qrRead(String result){
        this.btnReadQR.setText("Leer QR");
        openVisitWindow(result);
    }
    void openVisitWindow(String qrText){
        try {
            System.out.println(qrText);
            JSONData data = new JSONData(qrText);
            VisitaController.abrir(data,this,nodo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void changeDB(ActionEvent event) {
        if (isEditingDB){
            setDBEditingMode(false);
            saveDBEditing();
        }else{
            setDBEditingMode(true);
        }
    }

    @FXML
    void readQR(ActionEvent event) {
        stopWebcam=!stopWebcam;
        if (!stopWebcam) {
            this.btnReadQR.setText("Dejar de leer");
            initWebcam();
            this.webcam.open();
            ExecutorService servicio = Executors.newCachedThreadPool();
            servicio.execute(new Runnable() {
                @Override
                public void run() {
                    readQR();
                }
            });
        }else {
            this.btnReadQR.setText("Leer QR");
        }
    }

    @FXML
    void registrarNuevo(ActionEvent event) {
        VisitaController.abrirModoRegistrar(this,nodo);
        this.recargarVisitantes();
    }

    @FXML
    void reprintSelected(ActionEvent event) {
        tabla.getSelectionModel().getSelectedItems().forEach(visitante -> Impresora.imprimirCodigoQR(new JSONData(visitante),visitante.getAlias()));
        Mensajes.mostrarMensajeInformacion(null,"Imprimiendo",nodo);
    }
    @FXML
    void modificarVisitante(ActionEvent event) {
        VisitaController.abrirModoModificar(tabla.getSelectionModel().getSelectedItem(),this,nodo);
        this.recargarVisitantes();
    }
    @FXML
    void recargarVisitantes() {
        try {
            this.datosTabla.clear();
            this.datosTabla.addAll(VisitanteFX.selectAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    void testDBConnection(ActionEvent event) {
        try(Connection con=Conexion.obtener()) {
            con.close();
            Mensajes.mostrarMensajeInformacion(null,"Conexion exitosa",nodo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    void setDBEditingMode(boolean value){
        btnEditDB.setText(value?"Guardar":"Editar");
        btnTestConnection.setDisable(value);
        tfDBRoute.setDisable(!value);
        tfDBUser.setDisable(!value);
        pfDBPassword.setDisable(!value);
        isEditingDB=value;
    }
    void saveDBEditing(){
        Preferencias.put(Preferencias.Key.DB_ROUTE,tfDBRoute.getText());
        Preferencias.put(Preferencias.Key.DB_USER,tfDBUser.getText());
        Preferencias.put(Preferencias.Key.DB_PASSWORD,pfDBPassword.getText());
    }
}
