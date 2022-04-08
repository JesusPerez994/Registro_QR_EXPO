package controladores;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controlador {
    public static FXMLLoader getFXMLLoader(Object context, String fxml){
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource(String.format("/FXMLS/%s.fxml", fxml)));
        return loader;
    }
    public static Stage getStage(FXMLLoader loader, Node root_node, String titulo)throws IOException {
        Stage stage=new Stage();
        //stage.getIcons().add(IconoAplicacion.getIconoAplicacion(root_node.getScene().getWindow().getClass()));
        stage.setTitle(titulo);
        stage.setScene(new Scene(loader.load()));
        return stage;
    }
    public static Stage getStageApplicationModal(FXMLLoader loader, Node root_node, String titulo) throws IOException {
        Stage stage = new Stage();
        //stage.getIcons().add(IconoAplicacion.getIconoAplicacion(root_node.getScene().getWindow().getClass()));
        stage.setTitle(titulo);
        stage.setScene(new Scene(loader.load()));
        stage.initOwner(root_node.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }
}
