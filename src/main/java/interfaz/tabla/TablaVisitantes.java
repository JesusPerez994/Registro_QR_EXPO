package interfaz.tabla;

import identidadesFX.VisitanteFX;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class TablaVisitantes extends Tabla<VisitanteFX>{

    TableColumn colEmpresa,colNombre,colTelefono,colMail,colEstado,colCiudad,colCp,colAlias;

    @Override
    void buildColumnas() {
        colEmpresa = new TableColumn("EMPRESA");
        colNombre = new TableColumn("NOMBRE");
        colTelefono = new TableColumn("TELEFONO");
        colMail = new TableColumn("MAIL");
        colEstado = new TableColumn("ESTADO");
        colCiudad = new TableColumn("CIUDAD");
        colCp = new TableColumn("CP");
        colAlias = new TableColumn("ALIAS");

        getColumns().addAll(colEmpresa,colNombre,colTelefono,colMail,colEstado,colCiudad,colCp,colAlias);

        //Tamaos
        //col_producto.setPrefWidth(200);
        //col_codigo.setPrefWidth(100);
        //Vistas
        //col_precio.setVisible(false);
    }

    @Override
    void buildPropiedadesColumnas() {
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colCp.setCellValueFactory(new PropertyValueFactory<>("cp"));
        colAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
    }

    @Override
    void buildColumnasEditables() {

    }
}
