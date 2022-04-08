/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import format.Formato;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 *
 * @author jesue
 */
public interface Mensajes {
    public static Optional<ButtonType> mostrarMensaje(Alert.AlertType tipo, String header, String content,Node node) {
        Alert alerta = new Alert(tipo);
        alerta.initOwner(node.getScene().getWindow());
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        return alerta.showAndWait();
    }
    public static void mostrarMensajeInformacion(String header, String content,Node node){
        mostrarMensaje(Alert.AlertType.INFORMATION,header,content,node);
    }
    public static void mostrarMensajeError(String content,String error,Node node){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        if (node!=null) {
            alerta.initOwner(node.getScene().getWindow());
        }
        alerta.setHeaderText("Error");
        alerta.setContentText(content);
        if (error!=null){
            alerta.getDialogPane().setExpandableContent(new TextArea(error));
        }
        alerta.showAndWait();
    }
    public static <T> Optional<T> mostrarChoiceDialog(String header,ObservableList<T>datos,Node node){
        ChoiceDialog<T> dialog = new ChoiceDialog();
        dialog.setHeaderText(header);
        dialog.initOwner(node.getScene().getWindow());
        dialog.getItems().addAll(datos);
        dialog.setSelectedItem(datos.get(0));
        return dialog.showAndWait();
    }
    public static Optional<String> mostrarInputDialog(Formato.Type formato, String header, String content, Node node){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        dialog.initOwner(node.getScene().getWindow());
        if (formato!=null){
            Formato.setFormato(dialog.getEditor(),formato);
        }
        return dialog.showAndWait();
    }
    public static boolean mostrarMensajeConfirmacion(String header,String content,Node root_node){
        Optional<ButtonType>res = mostrarMensaje(Alert.AlertType.CONFIRMATION,header,content,root_node);
        if (res.isPresent()){
            if (res.get().getButtonData().isDefaultButton()){
                return true;
            }
        }
        return false;
    }
    public static boolean mostrarMensajeSiNo(String header,String content,Node root_node){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        alerta.initOwner(root_node.getScene().getWindow());
        alerta.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType>res = alerta.showAndWait();
        if (res.isPresent()){
            if (res.get().equals(ButtonType.YES)){
                return true;
            }
        }
        return false;
    }
    public static boolean mostrarMensajeAdvertencia(String header,String content,Node root_node){
        Optional<ButtonType>res = mostrarMensaje(Alert.AlertType.WARNING,header,content,root_node);
        if (res.isPresent()){
            if (res.get().getButtonData().isDefaultButton()){
                return true;
            }
        }
        return false;
    }
    public static Optional<ButtonType> mostrarMensajeConfirmacion(String header,String content,Node root_node, ButtonType...botones){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText(header);
        alerta.setContentText(content);
        alerta.initOwner(root_node.getScene().getWindow());
        alerta.getButtonTypes().setAll(botones);
        alerta.getButtonTypes().add(new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE));
        return alerta.showAndWait();
    }
}
