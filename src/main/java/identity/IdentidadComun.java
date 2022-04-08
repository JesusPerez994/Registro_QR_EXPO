/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import misql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author jesue
 */
public interface IdentidadComun extends Identidad{

    public Columna getColumnaId();

    public default Tabla getTabla() {
        return getColumnaId().T();
    }

    public default boolean exists(String id) throws SQLException {
        return !select(id).isEmpty();
    }
    public default List<Object[]> select(String id) throws SQLException {
        Condicion condicion = new Condicion(getColumnaId(), Condicion.Type.IGUAL,id);
        return SentenciasSQL.select(getTabla(),null,null,condicion,null);
    }
    public default ObservableList<IdentidadSimpleFX> selectAllAsIdentidadSimple() throws SQLException{
        List<Object[]>res = selectAll();
        ObservableList<IdentidadSimpleFX>lista = FXCollections.observableArrayList();
        res.forEach(r->lista.add(new IdentidadSimpleFX((String)r[0],(String)r[1])));
        return lista;
    };

    public default String insertAndReturnUUID(LinkedHashMap<Columna,Object>data) throws SQLException {
        try(Connection con = Conexion.obtener()){
            return insertAndReturnUUID(con,data);
        }
    }
    public default String insertAndReturnUUID(Connection con,LinkedHashMap<Columna,Object>data) throws SQLException {
        String uuid=UUID.randomUUID().toString();
        data.put(getColumnaId(),uuid);
        System.out.println(data.entrySet());
        insert(con,data);
        return uuid;
    }
    public default void update(String id, Columna c_nuevo, Object nuevo) throws SQLException {
        update( getMap(c_nuevo, nuevo),getMapId(id));
    }

    public default void update(Connection con, String id, Columna c_nuevo, Object nuevo) throws SQLException {
        update(con,getMap(c_nuevo, nuevo),getMapId(id));
    }

    public default void update(String id, LinkedHashMap<Columna,Object>data) throws SQLException {
        update(data,getMapId(id));
    }

    public default void update(Connection con, String id, LinkedHashMap<Columna,Object>data) throws SQLException {
        update(con,data,getMapId(id));
    }

    public default void delete(String id) throws SQLException {
        delete(getMapId(id));
    }

    public default void delete(Connection con, String id) throws SQLException {
        delete(con, getMapId(id));
    }
    public default void deleteIn(List<String>ids) throws SQLException {
        try(Connection con=Conexion.obtener()){
            deleteIn(con,ids);
        }
    }
    public default void deleteIn(Connection con,List<String> ids)throws SQLException{
        LinkedHashMap<Columna,List<Object>>valores=new LinkedHashMap<>();
        valores.put(getColumnaId(), (List)ids);
        deleteIn(con,valores);
    }
    default LinkedHashMap<Columna,Object> getMapId(Object id){
        LinkedHashMap<Columna,Object> lhmp = new LinkedHashMap<>();
        lhmp.put(getColumnaId(),id);
        return lhmp;
    }

}
