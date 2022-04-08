package identidades;

import identity.IdentidadSimple;
import misql.Columna;
import misql.Tabla;
import misql.Tablas;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Empresa extends IdentidadSimple {
    public enum C implements Columna {
        id_empresa, nombre;
        Tabla T = Tablas.empresa;

        @Override
        public Tabla T() {
            return this.T;
        }
    }
    private static Empresa empresa;

    public static Empresa getInstance(){
        return empresa==null?new Empresa(Visitante.C.id_empresa, C.id_empresa, C.nombre):empresa;
    }
    public Empresa(Columna C_id_padre, Columna C_id, Columna C_nombre) {
        super(C_id_padre, C_id, C_nombre);
    }

    public void insertarConIdInt(String empresa) throws SQLException {
        LinkedHashMap emp=new LinkedHashMap();
        emp.put(Empresa.C.nombre,empresa);
        super.insert(emp);

    }
}
