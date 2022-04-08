package interfaz.tabla;


import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.util.function.Consumer;

public abstract class TablaAbstracta<S> extends TableView<S> {
    TablaAbstracta(){
        buildColumnas();
        buildPropiedadesColumnas();
        buildColumnasEditables();
    }
    abstract void buildColumnas();
    abstract void buildPropiedadesColumnas();
    abstract void buildColumnasEditables();

/*    public void setOnDoubleClickRow( Consumer<S>consumer, Permisos.Permiso permiso){
        this.setRowFactory( tv -> {
            TableRow<S> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    if (!UsuarioActual.tienePermiso(permiso)) {
                        if (PermisoController.concederPermiso(permiso,this,this)){
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
    public void setOnDoubleClickRow(Consumer<S> consumer){
        this.setRowFactory( tv -> {
            TableRow<S> row = new TableRow();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    consumer.accept(row.getItem());
                }
            });
            return row ;
        });
    }
}
