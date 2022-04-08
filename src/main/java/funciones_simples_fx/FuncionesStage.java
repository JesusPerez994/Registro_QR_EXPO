package funciones_simples_fx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface FuncionesStage {
    public static void cerrar(Node nodo){
        getStageFromNode(nodo).close();
    }
    public static void setOnCerrar(Node nodo,Consumer<WindowEvent> consumer){
        Stage stage = getStageFromNode(nodo);
        stage.setOnCloseRequest(e->consumer.accept(e));
    }
    public static Stage getStageFromNode(Node node){
        return ((Stage)node.getScene().getWindow());
    }
}
