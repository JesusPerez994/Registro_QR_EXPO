package identidades;

import identity.SuperIdentidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import misql.*;

import java.sql.SQLException;
import java.util.List;

public class Ciudad extends SuperIdentidad {
    public Ciudad(Columna C_id) {
        super(C_id);
    }
    private static Ciudad c;
    public static Ciudad getInstancia(){
        if (c==null) {
            c=new Ciudad(C.id_ciudad);
        }
        return c;
    }
    public enum C implements Columna{
        id_ciudad,id_estado,nombre;
        Tabla T= Tablas.ciudad;
        @Override
        public Tabla T() {
            return this.T;
        }
    }


}
