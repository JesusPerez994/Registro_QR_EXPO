package identity;

import misql.Columna;
import misql.Condicion;
import misql.SentenciasSQL;
import misql.Tabla;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

public interface Identidad {

    public Tabla getTabla();

    public default List<Object[]> selectAll() throws SQLException {
        return SentenciasSQL.select(getTabla(),null);
    }
    public default List<Object[]> selectBy(Columna columna, Object value) throws SQLException {
        Condicion condicion = new Condicion(columna, Condicion.Type.IGUAL,value);
        return SentenciasSQL.select(getTabla(),null,null,condicion,null);
    }
    public default int insert(LinkedHashMap<Columna,Object>data) throws SQLException {
        return SentenciasSQL.insert(getTabla(), data);
    }
    public default void insert(Connection con,LinkedHashMap<Columna,Object>data) throws SQLException {
        SentenciasSQL.insert(con,getTabla(), data);
    }
    public default void insert(List<Columna>columnas,List<List<Object>>datos) throws SQLException {
        SentenciasSQL.insert(getTabla(),columnas,datos);
    }
    public default void insert(Connection con,List<Columna>columnas,List<List<Object>>datos) throws SQLException {
        SentenciasSQL.insert(con,getTabla(),columnas,datos);
    }
    public default void replace(LinkedHashMap<Columna,Object>data) throws SQLException{
        SentenciasSQL.replace(getTabla(),data);
    }
    public default void replace(Connection con,LinkedHashMap<Columna,Object>data) throws SQLException{
        SentenciasSQL.replace(con,getTabla(),data);
    }
    public default void replace(List<Columna>columnas,List<List<Object>>datos)throws SQLException{
        SentenciasSQL.replace(getTabla(),columnas,datos);
    }
    public default void replace(Connection con,List<Columna>columnas,List<List<Object>>datos)throws SQLException{
        SentenciasSQL.replace(con,getTabla(),columnas,datos);
    }
    public default void update(LinkedHashMap<Columna,Object>data,LinkedHashMap<Columna,Object>where) throws SQLException {
        SentenciasSQL.update(getTabla(),data,where);
    }
    public default void update(Connection con,LinkedHashMap<Columna,Object>data,LinkedHashMap<Columna,Object>where) throws SQLException {
        SentenciasSQL.update(con,getTabla(),data,where);
    }
    public default void add(LinkedHashMap<Columna,Integer>data, LinkedHashMap<Columna,Object>where) throws SQLException{
        SentenciasSQL.add(getTabla(),data,where);
    }
    public default void add(Connection con, LinkedHashMap<Columna,Integer>data, LinkedHashMap<Columna,Object>where) throws SQLException{
        SentenciasSQL.add(con,getTabla(),data,where);
    }
    public default void delete(LinkedHashMap<Columna,Object>where) throws SQLException {
        SentenciasSQL.delete(getTabla(),where);
    }
    public default void delete(Connection con,LinkedHashMap<Columna,Object>where) throws SQLException {
        SentenciasSQL.delete(con,getTabla(),where);
    }
    public default void deleteIn(LinkedHashMap<Columna,List<Object>>valores) throws SQLException {
        SentenciasSQL.deleteIn(getTabla(),valores);
    }
    public default void deleteIn(Connection con,LinkedHashMap<Columna,List<Object>>valores) throws SQLException {
        SentenciasSQL.deleteIn(con,getTabla(),valores);
    }
    public default LinkedHashMap<Columna,Object> getMap(Columna columna, Object dato){
        LinkedHashMap<Columna,Object> lhmp = new LinkedHashMap<>();
        lhmp.put(columna,dato);
        return lhmp;
    }
    public default LinkedHashMap<Columna,Integer> getMapCantidad(Columna columna, int dato){
        LinkedHashMap<Columna,Integer> lhmp = new LinkedHashMap<>();
        lhmp.put(columna,dato);
        return lhmp;
    }
}
