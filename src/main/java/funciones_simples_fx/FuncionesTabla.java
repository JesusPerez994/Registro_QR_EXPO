package funciones_simples_fx;


import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.function.Consumer;

public interface FuncionesTabla {

 /*   public static<T> void setOnDoubleClickRow(Object context,TableView<T>tabla, Consumer<T>consumer, Permisos.Permiso permiso){
        tabla.setRowFactory( tv -> {
            TableRow<T> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    if (!UsuarioActual.tienePermiso(permiso)) {
                        if (PermisoController.concederPermiso(permiso,context,tabla)){
                            consumer.accept(row.getItem());
                        }
                    }else{
                        consumer.accept(row.getItem());
                    }
                }
            });
            return row ;
        });
    }*/
    public static<T> void setOnDoubleClickRow(TableView<T> tabla, Consumer<T> consumer){
        tabla.setRowFactory( tv -> {
            TableRow<T> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    consumer.accept(row.getItem());
                }
            });
            return row ;
        });
    }
    public static MenuItem getMenuItemCopySelectedCells(TableView<?> tabla){
        MenuItem copiar=new MenuItem("Copiar");
        copiar.setOnAction(a->{
            ObservableList<TablePosition> posList=tabla.getSelectionModel().getSelectedCells();
            int old_r = -1;
            StringBuilder clipboardString = new StringBuilder();
            for (TablePosition p : posList) {
                int r = p.getRow();
                Object cell = tabla.getVisibleLeafColumns().get(p.getColumn()).getCellData(r);
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
    public static MenuItem getMenuItemCopyAllTable(TableView<?> tabla){
        MenuItem copiar_tabla=new MenuItem("Copiar tabla");
        copiar_tabla.setOnAction(a->{
            int columnas = tabla.getVisibleLeafColumns().size();
            int filas = tabla.getItems().size();
            StringBuilder clipboardString = new StringBuilder();
            for (int f = 0; f < filas; f++) {
                if (f!=0){
                    clipboardString.append('\n');
                }
                for (int c = 0; c < columnas; c++) {
                    Object cell = tabla.getVisibleLeafColumn(c).getCellData(f);
                    if (cell==null){
                        cell="";
                    }
                    else if (cell.toString()==null)
                        cell="";
                    clipboardString.append(cell);
                    if (c<columnas-1){
                        clipboardString.append('\t');
                    }

                }
            }
            final ClipboardContent content = new ClipboardContent();
            content.putString(clipboardString.toString());
            Clipboard.getSystemClipboard().setContent(content);
        });
        return copiar_tabla;
    }
}
