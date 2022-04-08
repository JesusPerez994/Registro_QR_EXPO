package identity;

import misql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface IdentidadIdDoble extends Identidad {

    public Columna getColumnaId1();
    public Columna getColumnaId2();

    public default Tabla getTabla() {
        return getColumnaId1().T();
    }
    public default boolean exists(String id1,String id2) throws SQLException {
        return !select(id1,id2).isEmpty();
    }
    public default List<Object[]> select(String id1,String id2) throws SQLException {
        Condicion condicion = new Condicion(getColumnaId1(), Condicion.Type.IGUAL,id1).AND(
                new Condicion(getColumnaId2(), Condicion.Type.IGUAL,id2)
        );
        return SentenciasSQL.select(getTabla(),null,null,condicion,null);
    }

    public default void update(String id1,String id2, Columna c_nuevo, Object nuevo) throws SQLException {
        update(getMap(c_nuevo, nuevo),getMapId(id1,id2));
    }

    public default void update(Connection con, String id1,String id2, Columna c_nuevo, Object nuevo) throws SQLException {
        update(con, getMap(c_nuevo, nuevo),getMapId(id1,id2));
    }

    public default void update(String id1,String id2, LinkedHashMap<Columna,Object> data) throws SQLException {
        update(data,getMapId(id1,id2));
    }

    public default void update(Connection con, String id1,String id2, LinkedHashMap<Columna,Object>data) throws SQLException {
        update(con,data,getMapId(id1,id2));
    }
    public default void add(String id1,String id2,LinkedHashMap<Columna,Integer>data) throws SQLException {
        add(data,getMapId(id1,id2));
    }
    public default void add(Connection con,String id1,String id2,LinkedHashMap<Columna,Integer>data) throws SQLException {
        add(con,data,getMapId(id1,id2));
    }
    public default void delete(String id1,String id2) throws SQLException {
        delete(getMapId(id1,id2));
    }
    public default void delete(Connection con, String id1,String id2) throws SQLException {
        delete(con,getMapId(id1,id2));
    }
    public default void deleteInCompositeKey(String id_1,List<String>ids_2) throws SQLException {
        try(Connection con = Conexion.obtener()){
            deleteInCompositeKey(con,id_1,ids_2);
        }
    }
    public default void deleteInCompositeKey(Connection con,String id_1,List<String>ids_2) throws SQLException {
        List<String>lista = new ArrayList<>();
        for (int i = 0; i < ids_2.size(); i++) {
            lista.add(id_1);
        }
        deleteInCompositeKey(con,lista,ids_2);
    }
    public default void deleteInCompositeKey(List<String>ids1,List<String>ids2) throws SQLException {
        try(Connection con= Conexion.obtener()){
            deleteInCompositeKey(con,ids1,ids2);
        }
    }
    public default void deleteInCompositeKey(Connection con,List<String> ids1,List<String>ids2)throws SQLException{
        LinkedHashMap<Columna,List<Object>>valores=new LinkedHashMap<>();
        valores.put(getColumnaId1(), (List)ids1);
        valores.put(getColumnaId2(),(List)ids2);
        SentenciasSQL.deleteInCompositeKey(con,getTabla(),valores);
    }
    public default LinkedHashMap<Columna,Object> getMapId(Object id1, Object id2){
        LinkedHashMap<Columna,Object> lhmp = new LinkedHashMap<>();
        lhmp.put(getColumnaId1(),id1);
        lhmp.put(getColumnaId2(),id2);
        return lhmp;
    }
}
