/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

/**
 *
 * @author jesue
 */
public enum Tablas implements Tabla {
    ciudad("ciu"),
    clasificacion("cla"),
    empresa("emp"),
    estado("est"),
    visita("vta"),
    visitante("vte");

    
    private final String id;
    Tablas(String id){
        this.id=id;
    }
    @Override
    public String getId(){
        return this.id;
    }
    
}