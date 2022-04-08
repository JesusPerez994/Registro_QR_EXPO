package identidades;

import data.Fecha;
import identity.SuperIdentidad;
import misql.Columna;
import misql.Tabla;
import misql.Tablas;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Visita extends SuperIdentidad {
    public enum C implements Columna {
        id_visita,id_visitante,fecha,asistentes;
        @Override
        public Tabla T() {
            return Tablas.visita;
        }
    }
    private static Visita visita;
    Visita(Columna C_id) {
        super(C_id);
    }
    public static Visita getInstance(){ return visita==null?new Visita(C.id_visita):visita;}

    public void insertarVisita(String id_visitante, String asistentes) throws SQLException {
        LinkedHashMap map = new LinkedHashMap();
        map.put(C.id_visitante,id_visitante);
        map.put(C.fecha, Fecha.getDate());
        map.put(C.asistentes, asistentes);
        super.insert(map);
    }
}
