/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identity;

import misql.Columna;

import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 *
 * @author jesue
 */
public abstract class SuperIdentidad implements IdentidadComun {

    
    Columna C_id;

    public SuperIdentidad(Columna C_id) {
        this.C_id = C_id;
    }

    @Override
    public Columna getColumnaId(){
        return this.C_id;
    }
}
