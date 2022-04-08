import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import controladores.Controlador;
import data.Preferencias;
import format.Formato;
import funciones_simples_fx.FuncionesComboBox;
import funciones_simples_fx.FuncionesStage;
import funciones_simples_fx.IdFormat;
import identidades.*;
import identidadesFX.VisitanteFX;
import identity.IdentidadSimpleFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import json.JSONData;
import modelos.CiudadModel;
import modelos.VisitanteModel;
import org.json.JSONException;
import printer.CodigoQR;
import printer.Etiqueta;
import printer.Impresora;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static messages.Mensajes.mostrarMensajeError;
import static messages.Mensajes.mostrarMensajeInformacion;

public class VisitaController implements Initializable {

    @FXML
    private TextField tfId, tfEmpresa,tfNombre,tfTelefono, tfMail,tfCp, tfGafete,tfInvitados;

    @FXML
    private Label labelId,labelInvitados;

    @FXML
    private ComboBox<IdentidadSimpleFX> cbClasificacion,cbEstado;
    @FXML
    private ComboBox<CiudadModel>cbCiudad;

    private ObservableList<IdentidadSimpleFX>empresas=FXCollections.observableArrayList();

    VisitanteModel visitanteModel;
    FilteredList<CiudadModel> listaCiudades;
    boolean isInDB=false;
    boolean modoRegistrar=false;
    boolean modoModificar=false;
    Node nodo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nodo=tfId;
        buildFormatos();
        tfId.setEditable(false);
        tfEmpresa.setEditable(false);
        tfNombre.setEditable(false);
        tfTelefono.setEditable(false);
        tfMail.setEditable(false);
        tfCp.setEditable(false);
        try {
            cbEstado.setItems(Estado.getInstancia().selectAllAsIdentidadSimple());
            this.listaCiudades=new FilteredList(FXCollections.observableArrayList(Ciudad.getInstancia().selectAll().stream().map(re->new CiudadModel(re)).collect(Collectors.toList())), p->true);
            cbCiudad.setItems(this.listaCiudades);
            cbEstado.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                listaCiudades.setPredicate(p->{
                    //String id_estado = cb_estado.getSelectionModel().getSelectedItem().getId();
                    int id_estado=Integer.parseInt(newValue.getId());
                    return p.getIdEstado()==id_estado;
                });
                cbCiudad.getSelectionModel().select(0);
            });
            cbEstado.getSelectionModel().select(0);
            FuncionesComboBox.searchWithKey(cbCiudad);
            FuncionesComboBox.searchWithKey(cbEstado);
            FuncionesComboBox.searchWithKey(cbClasificacion);
            cbClasificacion.setItems(Clasificacion.getInstance().selectAllAsIdentidadSimple());
            cbClasificacion.getSelectionModel().select(0);
            updateEmpresas();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void buildFormatos(){
        Formato.setFormato(tfEmpresa,Formato.Type.TEXTO);
        Formato.setFormato(tfNombre,Formato.Type.TEXTO);
        Formato.setFormato(tfTelefono,Formato.Type.TELEFONO);
        Formato.setFormato(tfMail,Formato.Type.CORREO);
        Formato.setFormato(tfCp,Formato.Type.TELEFONO);
        Formato.setFormato(tfGafete,Formato.Type.TEXTO);
        Formato.setFormato(tfInvitados,Formato.Type.ENTERO_POSITIVO);
    }

    private void updateEmpresas() throws SQLException {
        this.empresas.clear();
        this.empresas.addAll(Empresa.getInstance().selectAllAsIdentidadSimple());
    }

    public void iniciar(JSONData data){
        tfId.setText(data.getId());
        tfEmpresa.setText(data.getEmpresa());
        tfNombre.setText(data.getContacto());
        tfTelefono.setText(data.getTel());
        tfMail.setText(data.getEmail());
        tfCp.setText(data.getCp());

        findInDataBase(data);
    }
    public void modoRegistrar(){
        tfEmpresa.setEditable(true);
        tfNombre.setEditable(true);
        tfTelefono.setEditable(true);
        tfMail.setEditable(true);
        tfCp.setEditable(true);
        tfId.setVisible(false);
        //tfInvitados.setVisible(false);
        labelId.setVisible(false);
        //labelInvitados.setVisible(false);
        this.modoRegistrar=true;
    }
    public void modoModificar(VisitanteFX visitante){
        this.modoModificar=true;
        tfId.setVisible(false);
        tfInvitados.setVisible(false);
        labelId.setVisible(false);
        labelInvitados.setVisible(false);

        tfId.setText(IdFormat.buildId(visitante.getId()));
        tfEmpresa.setText(visitante.getEmpresa());
        tfNombre.setText(visitante.getNombre());
        tfTelefono.setText(visitante.getTelefono());
        tfMail.setText(visitante.getMail());
        tfCp.setText(visitante.getCp());
        tfGafete.setText(visitante.getAlias());

        cbEstado.getSelectionModel().select(cbEstado.getItems().stream().filter(e->e.getNombre().equals(visitante.getEstado())).findFirst().get());
        cbCiudad.getSelectionModel().select(listaCiudades.stream().filter(e->e.getNombre().equals(visitante.getCiudad())).findFirst().get());
        cbClasificacion.getSelectionModel().select(cbClasificacion.getItems().stream().filter(e->e.getNombre().equals(visitante.getClasificacion())).findFirst().get());

        try {
            this.visitanteModel = VisitanteModel.findById(visitante.getId()+"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void findInDataBase(JSONData data){
        try{
            if (data.isExterno()){
                visitanteModel=VisitanteModel.findByIdExterno(data.getId());
            }else{
                visitanteModel = VisitanteModel.findById(IdFormat.getIdFromText(data.getId()));
            }
            if (visitanteModel==null){
                visitanteModel=VisitanteModel.findByIdExterno(data.getId());
            }
            if (visitanteModel!=null){
                List <Object[]>ciudadRes=Ciudad.getInstancia().select(visitanteModel.getId_ciudad()+"");
                cbClasificacion.getSelectionModel().select(cbClasificacion.getItems().stream().filter(c->c.getId().equals(visitanteModel.getId_clasificacion()+"")).findFirst().get());
                int id_ciudad = visitanteModel.getId_ciudad();
                int id_estado = (int) Estado.getInstancia().select(ciudadRes.get(0)[1]+"").get(0)[0];
                cbEstado.getSelectionModel().select(cbEstado.getItems().stream().filter(e->e.getId().equals(id_estado+"")).findFirst().get());
                cbCiudad.getSelectionModel().select(listaCiudades.stream().filter(c->c.getIdCiudad()==id_ciudad).findFirst().get());
                tfGafete.setText(visitanteModel.getAlias());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            mostrarMensajeError("Error",throwables.getMessage(),nodo);
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        cerrar();
    }

    @FXML
    void registrar(ActionEvent event) {
        if (validarCampos()) {
            try {
                int id_visitante;
                if (modoRegistrar){
                    id_visitante=registrarNuevo();
                    printQRCode(id_visitante);
                }else{
                    if (visitanteModel!=null) {
                        id_visitante=visitanteModel.getId_visitante();
                        LinkedHashMap map = new LinkedHashMap();
                        map.put(Visitante.C.id_ciudad,cbCiudad.getSelectionModel().getSelectedItem().getIdCiudad());
                        map.put(Visitante.C.alias,tfGafete.getText());
                        map.put(Visitante.C.id_clasificacion,cbClasificacion.getSelectionModel().getSelectedItem().getId());
                        Visitante.getInstance().update(id_visitante+"",map);
                    } else {
                        //Registrar
                        registrarNuevo();
                        List <Object[]>res=Visitante.getInstance().selectByIdExterno(tfId.getText());
                        visitanteModel = new VisitanteModel(res.get(0));
                        id_visitante=visitanteModel.getId_visitante();
                    }
                }
                if (!modoModificar) {
                    Visita.getInstance().insertarVisita(id_visitante + "", tfInvitados.getText().isEmpty() ? "0" : tfInvitados.getText());
                    mostrarMensajeInformacion(null, "Visita registrada", nodo);
                }else {
                    mostrarMensajeInformacion(null,"Visitante modificado",nodo);
                }
                cerrar();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private int registrarNuevo() throws SQLException {
        if (!yaExisteEmpresa()) {
            Empresa.getInstance().insertarConIdInt(tfEmpresa.getText());
        }
        String id_empresa = Empresa.getInstance().selectId(tfEmpresa.getText());
        return Visitante.getInstance().registrar(
                tfNombre.getText(),
                id_empresa,
                tfTelefono.getText(),
                tfMail.getText(),
                tfCp.getText(),
                tfGafete.getText(),
                cbCiudad.getSelectionModel().getSelectedItem(),
                cbClasificacion.getSelectionModel().getSelectedItem(),
                tfId.getText()
        );
    }
    private JSONData getJsonFromData(int id){
        try {
            return new JSONData(IdFormat.buildId(id),tfEmpresa.getText(),tfNombre.getText(),tfTelefono.getText(),tfMail.getText(),tfCp.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    void printQRCode(int id) {
        Impresora.imprimirCodigoQR(getJsonFromData(id),tfGafete.getText());
    }
    private boolean validarCampos(){
        if (tfEmpresa.getText().isEmpty()){
            mostrarMensajeError("Ingrese el nombre de la empresa",null,nodo);
            return false;
        }
        if (!tfInvitados.getText().isEmpty()) {
            try {
                Integer.parseInt(tfInvitados.getText());
            } catch (NumberFormatException e) {
                mostrarMensajeError("El numero de invitados debe ser escrito solo con numeros",null,nodo);
                return false;
            }
        }
        return true;
    }

    private boolean yaExisteEmpresa(){
        return empresas.stream().filter(e->e.getNombre().toUpperCase().equals(tfEmpresa.getText().toUpperCase())).findFirst().isPresent();
    }

    private void cerrar(){
        FuncionesStage.cerrar(nodo);
    }
    public static void abrir(JSONData data,Object context, Node nodo){
        FXMLLoader loader= Controlador.getFXMLLoader(context,"Visita");
        try {
            Stage stage=Controlador.getStageApplicationModal(loader,nodo,"Visita");
            VisitaController controller=loader.getController();
            controller.iniciar(data);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo abrir la ventana de visita",e.getMessage(),nodo);
        }
    }
    public static void abrirModoRegistrar(Object context, Node nodo){
        FXMLLoader loader= Controlador.getFXMLLoader(context,"Visita");
        try {
            Stage stage=Controlador.getStageApplicationModal(loader,nodo,"Registrar");
            VisitaController controller=loader.getController();
            controller.modoRegistrar();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo abrir la ventana de visita",e.getMessage(),nodo);
        }
    }
    public static void abrirModoModificar(VisitanteFX visitante,Object context, Node nodo){
        FXMLLoader loader= Controlador.getFXMLLoader(context,"Visita");
        try {
            Stage stage=Controlador.getStageApplicationModal(loader,nodo,"Modificar");
            VisitaController controller=loader.getController();
            controller.modoModificar(visitante);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("No se pudo abrir la ventana de visita",e.getMessage(),nodo);
        }
    }

}
