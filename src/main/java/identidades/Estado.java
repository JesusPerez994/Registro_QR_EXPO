package identidades;

import identity.IdentidadSimple;
import misql.Columna;
import misql.Tabla;
import misql.Tablas;

public class Estado extends IdentidadSimple {
    private static Estado e;

    Estado(Columna C_id, Columna C_nombre) {
        super(null,C_id,C_nombre);
    }
    public static Estado getInstancia(){
        if (e==null) {
            e=new Estado(C.id_estado,C.nombre);
        }
        return e;
    }

    public enum C implements Columna {
        id_estado, nombre;
        Tabla T = Tablas.estado;

        @Override
        public Tabla T() {
            return this.T;
        }
    }
}
