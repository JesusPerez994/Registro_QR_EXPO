/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identity;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jesue
 */
public class IdentidadSimpleFX {
    
    private final StringProperty id=new SimpleStringProperty();
    private final StringProperty nombre=new SimpleStringProperty();

    public IdentidadSimpleFX() {

    }
    public IdentidadSimpleFX(String id, String nombre) {
        this.id.setValue(id);
        this.nombre.setValue(nombre);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty nombreProperty(){
        return this.nombre;
    }
    public String getNombre() {
        return nombre.getValue();
    }
    public void setNombre(String nombre) {
        this.nombre.setValue(nombre);
    }

    public static String convertirParametrosANombre(Object... datos){
        String d=Arrays.stream(datos).map(m->m==null?"":m.toString()).collect(Collectors.joining(" "));
        return d;
    }
    public static Callback<IdentidadSimpleFX, Observable[]> extractor() {
        return (p-> new Observable[]{p.nombre});
    }
    @Override
    public String toString() {
        return nombre.getValue();
    }
    
}
