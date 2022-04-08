/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identity;


import javafx.scene.Node;
import misql.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import static messages.Mensajes.mostrarMensajeError;


/**
 *
 * @author jesue
 */
public abstract class IdentidadSimple extends SuperIdentidad {

    Columna C_id_padre;
    Columna C_nombre;

    public IdentidadSimple(Columna C_id_padre,Columna C_id,Columna C_nombre) {
        super(C_id);
        this.C_id_padre=C_id_padre;
        this.C_nombre=C_nombre;
    }
    public String selectId(String nombre) throws SQLException{
        try (Connection con = Conexion.obtener()) {
            return selectId(con, nombre);
        }
    }
    public String selectId(Connection con,String nombre) throws SQLException{
        List<Object[]>rs = selectIdentidadSimple(con,nombre);
        //Si no existe
        if (rs.isEmpty()){
            //Insertar como nuevo
            return super.insertAndReturnUUID(getMap(C_nombre,nombre));
        }else{
            //Retornar el id ya existente
            return rs.get(0)[0].toString();
        }
    }
    private List<Object[]> selectIdentidadSimple(Connection con,String nombre) throws SQLException {
        return SentenciasSQL.select(con,getTabla(),null,null,new Condicion(C_nombre, Condicion.Type.IGUAL,nombre),null);
    }
    public IdentidadSimpleFX insertar(String nombre, Node nodo) throws SQLException {
        try{
            String id=super.insertAndReturnUUID(getMap(C_nombre,nombre));
            return new IdentidadSimpleFX(id,nombre);
        }catch (SQLIntegrityConstraintViolationException e){
            mostrarMensajeError("El nombre ya se encuentra registrado",e.getMessage(),nodo);
        }
        return null;
    }
    public void remplazarPor( String id,String id_nuevo) throws SQLException{
        try(Connection con = Conexion.obtener()){
            remplazarPor(con,id,id_nuevo);
        }
    }
    public void remplazarPor(Connection con, String id,String id_nuevo) throws SQLException{
        SentenciasSQL.update(con,C_id_padre.T(),getMap(C_id,id_nuevo),getMap(C_id,id));
    }
    public void updateNombre(String id, Object nuevo) throws SQLException {
        super.update(id, C_nombre, nuevo); 
    }
    public ObservableList<IdentidadSimpleFX> selectAllAsIdentidadSimple() throws SQLException {
        Agrupacion agrupacion = new Agrupacion(Agrupacion.Type.ORDER_BY,C_nombre);
        List<Object[]> rs = SentenciasSQL.select(C_id.T(),null,null,null,agrupacion);
        ObservableList<IdentidadSimpleFX> lista = FXCollections.observableArrayList(IdentidadSimpleFX.extractor());
        rs.forEach((r) -> {
            lista.add(new IdentidadSimpleFX(r[0]+"", (String) r[1]));
        });
        return lista;
    }
    @Override
    public String toString() {
        String tabla=C_id.T().toString();
        return tabla.substring(0, 1).toUpperCase()+tabla.substring(1);
    }
    
}
