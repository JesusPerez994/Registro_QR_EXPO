package identidades;

import identity.IdentidadSimpleFX;
import identity.SuperIdentidad;
import misql.*;
import modelos.CiudadModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Visitante extends SuperIdentidad {
    public enum C implements Columna {
        id_visitante,nombre,telefono,email,cp,alias,id_ciudad,id_empresa,id_clasificacion,id_externo;
        @Override
        public Tabla T() {
            return Tablas.visitante;
        }
    }
    private static Visitante visitante;
    Visitante(Columna C_id) {
        super(C_id);
    }
    public static Visitante getInstance(){ return visitante==null?new Visitante(C.id_visitante):visitante;}

    public int registrar(String nombre, String id_empresa, String telefono, String email, String cp, String alias,CiudadModel ciudad, IdentidadSimpleFX clasificacion, String id_externo) throws SQLException {
        LinkedHashMap map = new LinkedHashMap();
        map.put(C.nombre,nombre);
        map.put(C.telefono,telefono);
        map.put(C.email,email);
        map.put(C.cp,cp);
        map.put(C.alias,alias);
        map.put(C.id_ciudad,ciudad.getIdCiudad());
        map.put(C.id_empresa,id_empresa);
        map.put(C.id_clasificacion,clasificacion.getId());
        map.put(C.id_externo,id_externo);
        return super.insert(map);
    }
    public List<Object[]> selectByIdExterno(String id_externo) throws SQLException {
        return super.selectBy(C.id_externo,id_externo);
    }

    public List<Object[]> selectAllVisitantesFX() throws SQLException {
        List columnas = new ArrayList();
        columnas.addAll(Arrays.asList(C.id_visitante,Empresa.C.nombre,C.nombre,C.telefono,C.email,Estado.C.nombre,Ciudad.C.nombre,C.cp,C.alias,Clasificacion.C.nombre,C.id_externo));
        Join join = new Join(Ciudad.C.id_ciudad,C.id_ciudad, Join.Type.JOIN).
                also(Estado.C.id_estado,Ciudad.C.id_estado, Join.Type.JOIN).
                also(Empresa.C.id_empresa,C.id_empresa, Join.Type.JOIN).
                also(Clasificacion.C.id_clasificacion,C.id_clasificacion, Join.Type.JOIN);
        return SentenciasSQL.select(getTabla(),columnas,join,null,null);
    }

}
