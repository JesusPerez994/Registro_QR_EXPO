package identidadesFX;

import identidades.Visitante;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitanteFX {

    IntegerProperty id = new SimpleIntegerProperty();
    StringProperty empresa = new SimpleStringProperty();
    StringProperty nombre = new SimpleStringProperty();
    StringProperty telefono = new SimpleStringProperty();
    StringProperty mail = new SimpleStringProperty();
    StringProperty estado = new SimpleStringProperty();
    StringProperty ciudad = new SimpleStringProperty();
    StringProperty cp = new SimpleStringProperty();
    StringProperty alias = new SimpleStringProperty();
    StringProperty clasificacion = new SimpleStringProperty();
    StringProperty idExterno = new SimpleStringProperty();



    public VisitanteFX(Object res[]){
        setId((Integer) res[0]);
        setEmpresa((String) res[1]);
        setNombre((String) res[2]);
        setTelefono((String) res[3]);
        setMail((String) res[4]);
        setEstado((String) res[5]);
        setCiudad((String) res[6]);
        setCp((String) res[7]);
        setAlias((String) res[8]);
        setClasificacion((String) res[9]);
        setIdExterno((String) res[10]);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getEmpresa() {
        return empresa.get();
    }

    public StringProperty empresaProperty() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa.set(empresa);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getEstado() {
        return estado.get();
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getCiudad() {
        return ciudad.get();
    }

    public StringProperty ciudadProperty() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getMail() {
        return mail.get();
    }

    public StringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getCp() {
        return cp.get();
    }

    public StringProperty cpProperty() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp.set(cp);
    }

    public String getAlias() {
        return alias.get();
    }

    public StringProperty aliasProperty() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias.set(alias);
    }

    public String getClasificacion() {
        return clasificacion.get();
    }

    public StringProperty clasificacionProperty() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion.set(clasificacion);
    }

    public String getIdExterno() {
        return idExterno.get();
    }

    public StringProperty idExternoProperty() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno.set(idExterno);
    }

    public static ObservableList<VisitanteFX> selectAll() throws SQLException {
        List<VisitanteFX>list=new ArrayList<>();
        List<Object[]>res=Visitante.getInstance().selectAllVisitantesFX();
        if (!res.isEmpty()){
            res.forEach(r->list.add(new VisitanteFX(r)));
        }
        return FXCollections.observableList(list);
    }
}
