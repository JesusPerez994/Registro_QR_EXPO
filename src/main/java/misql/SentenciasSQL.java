/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author jesue
 */
public interface SentenciasSQL {

    //<editor-fold defaultstate="collapsed" desc="Insertar">
    public static int insert(String consulta) throws SQLException {
        return insert(consulta, null);
    }

    public static int insert(String consulta, Object[] datos) throws SQLException {
        try (Connection con = Conexion.obtener()) {
            return insert(con, consulta, datos);
        }
    }

    public static int insert(Connection con, String consulta) throws SQLException {
        return insert(con, consulta, null);
    }

    public static int insert(Connection con, String consulta, Object[] datos) throws SQLException {
        System.out.println(consulta);
        try (PreparedStatement ps = con.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if (datos != null) {
                for (int i = 0; i < datos.length; i++) {
                    ps.setObject(i + 1, datos[i]);
                }
            }
            ps.execute();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
            }
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Seleccionar">

    public static ArrayList<Object[]> select(String consulta) throws SQLException {
        return select(consulta, null);
    }

    public static ArrayList<Object[]> select(String consulta, Object[] parametros) throws SQLException {
        try (Connection con = Conexion.obtener()) {
            return select(con, consulta, parametros);
        }
    }

    public static ArrayList<Object[]> select(Connection con, String consulta) throws SQLException {
        return select(con, consulta, null);
    }

    public static ArrayList<Object[]> select(Connection con, String consulta, Object[] parametros) throws SQLException {
        System.out.println(consulta);
        try (PreparedStatement ps = con.prepareStatement(consulta)) {
            if (parametros != null) {
                for (int i = 0; i < parametros.length; i++) {
                    ps.setObject(i + 1, parametros[i]);
                }
            }
            ResultSet rs = ps.executeQuery();
            int columnas = rs.getMetaData().getColumnCount();
            ArrayList<Object[]> lista = new ArrayList();
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                lista.add(fila);
            }
            return lista;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Modificar">

    public static void ejecutarSentencia(String consulta) throws SQLException {
        ejecutarSentencia(consulta, null);
    }

    public static void ejecutarSentencia(Connection con, String consulta) throws SQLException {
        ejecutarSentencia(con, consulta, null);
    }

    public static void ejecutarSentencia(String consulta, Object[] parametros) throws SQLException {
        try (Connection con = Conexion.obtener()) {
            ejecutarSentencia(con, consulta, parametros);
        }
    }

    public static void ejecutarSentencia(Connection con, String consulta, Object[] parametros) throws SQLException {
        System.out.println(consulta);
        PreparedStatement ps = con.prepareStatement(consulta);
        if (parametros != null) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
        }
        ps.execute();
    }

//</editor-fold>

    public static List<Object[]>select(Tabla tabla,List<? extends Columna>columnas) throws SQLException{
        return select(buildSelection(tabla,columnas, null, null, null,null));
    }
    public static List<Object[]>select(Connection con,Tabla tabla,List<? extends Columna>columnas) throws SQLException{
        return select(con,buildSelection(tabla, columnas, null, null, null,null));
    }
    public static List<Object[]> select(Tabla tabla,List<? extends Columna>columnas, Join joins,Condicion condicion,Agrupacion agrupaciones) throws SQLException{
        return select(buildSelection(tabla, columnas, joins, condicion, agrupaciones,null));
    }
    public static List<Object[]> select(Connection con,Tabla tabla,List<? extends Columna>columnas,Join joins,Condicion condicion,
                                        Agrupacion agrupaciones) throws SQLException{
        return select(con, buildSelection(tabla,columnas, joins, condicion, agrupaciones,null));
    }
    public static List<Object[]> select(Tabla tabla,List<? extends Columna>columnas,Join joins,Condicion condicion,
                                        Agrupacion agrupaciones,Having having) throws SQLException{
        return select(buildSelection(tabla,columnas, joins, condicion, agrupaciones,having));
    }
    public static List<Object[]> select(Connection con,Tabla tabla,List<? extends Columna>columnas,Join joins,Condicion condicion,
                                        Agrupacion agrupaciones,Having having) throws SQLException{
        return select(con, buildSelection(tabla,columnas, joins, condicion, agrupaciones,having));
    }
    
    public static String buildSelection(Tabla tabla,List<? extends Columna>columnas,
            Join joins,Condicion condicion,Agrupacion agrupaciones,Having having){
        String orders_by="";
        String consulta="SELECT";
        if (columnas==null){
            consulta+=" *";
        }else{
            consulta+=" "+columnas.stream().map(m->m.withTablaId()).collect(Collectors.joining(","));
        }
        consulta+=" FROM "+tabla.withId();
        if (joins!=null) {
            consulta+=" "+joins;
        }
        if (condicion!=null) {
            consulta+=" WHERE "+condicion;
        }
        if (agrupaciones!=null) {
            if (agrupaciones.toString().contains("GROUP BY")){
                consulta+=" "+agrupaciones;
            }else{
                orders_by+=" "+agrupaciones;
            }
        }
        if (having!=null){
            consulta+=" HAVING "+having;
        }
        if (!orders_by.isEmpty()){
            consulta+=orders_by;
        }
        return consulta;
    }

    public static int insert(Tabla tabla,LinkedHashMap<Columna,Object>data) throws SQLException {
        try(Connection con = Conexion.obtener()){
            return insert(con,tabla,data);
        }
    }
    public static int insert(Connection con,Tabla tabla,LinkedHashMap<Columna,Object>data) throws SQLException {
        String columnas = data.keySet().stream().map(c->c.toString()).collect(Collectors.joining(","));
        String signos = data.values().stream().map(d->"?").collect(Collectors.joining(","));
        String consulta = String.format("INSERT INTO %s (%s) VALUES (%s)",tabla.toString(),columnas,signos);
        return insert(con,consulta,data.values().toArray());
    }
    public static void insert(Tabla tabla,List<Columna>columnas,List<List<Object>>datos) throws SQLException {
        try(Connection con = Conexion.obtener()){
            insert(con,tabla,columnas,datos);
        }
    }
     static String buildInsert(Tabla tabla,List<Columna>columnas,List<List<Object>>datos){
        String cols=columnas.stream().map(m->m.toString()).collect(Collectors.joining(","));
        String signos = getSignosDeInterrogacion(columnas.size());
        String segmentos = datos.stream().map(m->String.format("(%s)",signos)).collect(Collectors.joining(","));
        return String.format("INSERT INTO %s (%s) VALUES %s",tabla.toString(),cols,segmentos);
    }
    public static void insert(Connection con,Tabla tabla,List<Columna>columnas,List<List<Object>>datos) throws SQLException {
        String consulta=buildInsert(tabla,columnas,datos);
        List<Object>data = datos.stream().flatMap(List::stream).collect(Collectors.toList());
        insert(con,consulta,data.toArray());
    }
    public static void insertAddOnDuplicateKey(Connection con,Tabla tabla,List<Columna>columnas,List<List<Object>>datos,Columna col_cant) throws SQLException {
        String consulta = buildInsert(tabla,columnas,datos);
        consulta+=String.format(" ON DUPLICATE KEY UPDATE %s=%<s + values(%<s)",col_cant.toString());
        List<Object>data = datos.stream().flatMap(List::stream).collect(Collectors.toList());
        insert(con,consulta,data.toArray());
    }
    public static void replace(Tabla tabla,LinkedHashMap<Columna,Object>data) throws SQLException{
        try(Connection con=Conexion.obtener()){
            replace(con,tabla,data);
        }
    }
    public static void replace(Connection con,Tabla tabla,LinkedHashMap<Columna,Object>data) throws SQLException {
        String columnas = data.keySet().stream().map(c->c.toString()).collect(Collectors.joining(","));
        String signos = data.values().stream().map(d->"?").collect(Collectors.joining(","));
        String consulta = String.format("REPLACE INTO %s (%s) VALUES (%s)",tabla.toString(),columnas,signos);
        ejecutarSentencia(con,consulta,data.values().toArray());
    }
    public static void replace(Tabla tabla,List<Columna>columnas,List<List<Object>>datos) throws SQLException{
        try(Connection con=Conexion.obtener()){
            replace(con,tabla,columnas,datos);
        }
    }
    public static void replace(Connection con,Tabla tabla,List<Columna>columnas,List<List<Object>>datos) throws SQLException {
        String cols=columnas.get(0).toString();
        String segmento="?";
        for (int i = 1; i < columnas.size(); i++) {
            cols+=","+columnas.get(i).toString();
            segmento+=",?";
        }
        List<Object>data=new ArrayList<>();
        data.addAll(datos.get(0));
        String segmentos=String.format("(%s)",segmento);
        for (int i = 1; i < datos.size(); i++) {
            segmentos+=String.format(",(%s)",segmento);
            data.addAll(datos.get(i));
        }
        String consulta = String.format("REPLACE INTO %s (%s) VALUES %s",tabla.toString(),cols,segmentos);
        ejecutarSentencia(con,consulta,data.toArray());
    }
    public static void update(Tabla tabla,LinkedHashMap<Columna,Object>data,LinkedHashMap<Columna,Object>where) throws SQLException {
        try(Connection con = Conexion.obtener()){
            update(con,tabla,data,where);
        }
    }
    public static void update(Connection con,Tabla tabla,LinkedHashMap<Columna,Object>data,LinkedHashMap<Columna,Object>where) throws SQLException {
        String columnas_data=getFormatoColumnaIgualADato(data,",");
        String columnas_where=getFormatoColumnaIgualADato(where," AND ");
        Collection<Object> datos=new ArrayList(data.values());
        datos.addAll(where.values());
        String consulta = String.format("UPDATE %s SET %s WHERE %s",tabla,columnas_data,columnas_where);
        datos.forEach(c-> System.out.println(c));
        SentenciasSQL.ejecutarSentencia(con,consulta,datos.toArray());
    }
    public static void add(Tabla tabla, LinkedHashMap<Columna,Integer>data, LinkedHashMap<Columna,Object>where)throws SQLException{
        try(Connection con = Conexion.obtener()){
            add(con,tabla,data,where);
        }
    }
    public static void add(Connection con, Tabla tabla, LinkedHashMap<Columna,Integer>data, LinkedHashMap<Columna,Object>where)throws SQLException{
        String columnas_data = getFormatoColumnaCantidad(data);
        String columnas_where = getFormatoColumnaIgualADato(where," AND ");
        Collection<Object> datos=new ArrayList(data.values());
        datos.addAll(where.values());
        String consulta = String.format("UPDATE %s SET %s WHERE %s",tabla,columnas_data,columnas_where);
        SentenciasSQL.ejecutarSentencia(con,consulta, datos.toArray());
    }

    public static void delete(Tabla tabla,LinkedHashMap<Columna,Object>where) throws SQLException {
        try(Connection con = Conexion.obtener()){
            delete(con,tabla,where);
        }
    }
    public static void delete(Connection con,Tabla tabla,LinkedHashMap<Columna,Object>where) throws SQLException {
        String consulta = String.format("DELETE FROM %s WHERE %s",tabla.toString(),getFormatoColumnaIgualADato(where," AND "));
        SentenciasSQL.ejecutarSentencia(con,consulta,where.values().toArray());
    }
    public static void deleteIn(Tabla tabla, LinkedHashMap<Columna,List<Object>>valores) throws SQLException {
        try(Connection con = Conexion.obtener()){
            deleteIn(con,tabla,valores);
        }
    }
    public static void deleteIn(Connection con, Tabla tabla, LinkedHashMap<Columna,List<Object>>valores) throws SQLException {
        String where=valores.entrySet().stream().map(
                    m->String.format("%s IN (%s)",m.getKey(),getSignosDeInterrogacion(m.getValue().size()))
            ).collect(Collectors.joining(" AND "));
        Object[]data=valores.values().stream().flatMap(List::stream).collect(Collectors.toList()).toArray();
        String consulta = String.format("DELETE FROM %s WHERE %s",tabla,where);
        SentenciasSQL.ejecutarSentencia(con,consulta,data);
    }
    public static void deleteInCompositeKey(Tabla tabla,LinkedHashMap<Columna,List<Object>>valores) throws SQLException {
        try(Connection con = Conexion.obtener()){
            deleteInCompositeKey(con,tabla,valores);
        }
    }
    public static void deleteInCompositeKey(Connection con, Tabla tabla, LinkedHashMap<Columna,List<Object>>valores) throws SQLException {
        String col_where=valores.keySet().stream().map(m->m.toString()).collect(Collectors.joining(","));
        String signos="";
        signos+=String.format("(%s)",getSignosDeInterrogacion(valores.size()));
        ArrayList<Object>lista=new ArrayList<>();
        for (int i = 0; i < valores.values().stream().findFirst().get().size(); i++) {
            if (i!=0){
                signos+=String.format(",(%s)",getSignosDeInterrogacion(valores.size()));
            }
            for (Map.Entry<Columna,List<Object>>entry:valores.entrySet()){
                lista.add(entry.getValue().get(i));
            }
        }
        String consulta= String.format("DELETE FROM %s WHERE (%s) IN (%s)",tabla,col_where,signos);
        SentenciasSQL.ejecutarSentencia(con,consulta,lista.toArray());
    }
     static String getFormatoColumnaIgualADato(LinkedHashMap<Columna,Object>data,String separacion){
        return data.entrySet().stream().map(
                s->String.format("%s=?",s.getKey())).collect(Collectors.joining(separacion));
    }
     static String getFormatoColumnaCantidad(LinkedHashMap<Columna,Integer>data){
        return data.entrySet().stream().map(s->String.format("%s=%s+?",s.getKey(),s.getKey())).collect(Collectors.joining(" "));
    }
    public static String getSignosDeInterrogacion(int cant){
        String signos="?";
        for (int i = 1; i < cant; i++) {
            signos+=",?";
        }
        return signos;
    }
}
