package interfaz.tabla;

import funciones_simples_fx.FuncionesTabla;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Consumer;

public abstract class Tabla<S> extends TablaAbstracta<S> {

    public Tabla(){
        this.getSelectionModel().setCellSelectionEnabled(true);
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buildContextMenu();
        this.setOnKeyPressed(eh->{
            if (eh.getCode().equals(KeyCode.DELETE)) {
                quitarSeleccionado();
            }
        });
    }

    void buildContextMenu(){
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(getMenuItemCopySelectedCells());
        menu.getItems().add(FuncionesTabla.getMenuItemCopyAllTable(this));
        setContextMenu(menu);
    }
    public void quitarSeleccionado(){
        ObservableList lista=this.getSelectionModel().getSelectedItems();
        if (!lista.isEmpty()){
            this.getItems().removeAll(lista);
        }
    }
    private MenuItem getMenuItemCopySelectedCells(){
        MenuItem copiar=new MenuItem("Copiar");
        copiar.setOnAction(a->{
            ObservableList<TablePosition> posList=this.getSelectionModel().getSelectedCells();
            int old_r = -1;
            StringBuilder clipboardString = new StringBuilder();
            for (TablePosition p : posList) {
                int r = p.getRow();
                Object cell = this.getVisibleLeafColumns().get(p.getColumn()).getCellData(r);
                if (cell==null)
                    cell="";
                else if (cell.toString()==null)
                    cell="";
                if (old_r == r)
                    clipboardString.append('\t');
                else if (old_r != -1)
                    clipboardString.append('\n');
                clipboardString.append(cell);
                old_r = r;
            }
            final ClipboardContent content = new ClipboardContent();
            content.putString(clipboardString.toString());
            Clipboard.getSystemClipboard().setContent(content);
        });
        return copiar;
    }
}
