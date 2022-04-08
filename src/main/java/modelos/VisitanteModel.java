package modelos;

import funciones_simples_fx.IdFormat;
import identidades.Visitante;
import identidadesFX.VisitanteFX;

import java.sql.SQLException;
import java.util.List;

public class VisitanteModel {

    int id_visitante;
    String nombre;
    String telefono;
    String email;
    String cp;
    String alias;



    int id_ciudad;
    int id_empresa;
    int id_clasificacion;

    String id_externo;

    public VisitanteModel(){}
    public VisitanteModel(Object[] res) {
        setId_visitante((int) res[0]);
        setNombre((String) res[1]);
        setTelefono((String) res[2]);
        setEmail((String) res[3]);
        setCp((String) res[4]);
        setAlias((String) res[5]);
        setId_ciudad((Integer) res[6]);
        setId_empresa((Integer) res[7]);
        setId_clasificacion((Integer) res[8]);
        setId_externo((String) res[9]);
    }

    public int getId_visitante() {
        return id_visitante;
    }

    public void setId_visitante(int id_visitante) {
        this.id_visitante = id_visitante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_clasificacion() {
        return id_clasificacion;
    }

    public void setId_clasificacion(int id_clasificacion) {
        this.id_clasificacion = id_clasificacion;
    }

    public String getId_externo() {
        return id_externo;
    }

    public void setId_externo(String id_externo) {
        this.id_externo = id_externo;
    }

    public static VisitanteModel findById(String id) throws SQLException {
        List<Object[]> res = Visitante.getInstance().select(id);
        if (!res.isEmpty()){
            return new VisitanteModel(res.get(0));
        }
        return null;
    }
    public static VisitanteModel findByIdExterno(String id_externo) throws SQLException {
        List<Object[]> res=Visitante.getInstance().selectByIdExterno(id_externo);
        if (!res.isEmpty()){
            return new VisitanteModel(res.get(0));
        }
        return null;
    }
}
