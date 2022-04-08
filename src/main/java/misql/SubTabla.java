/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

import java.util.ArrayList;
import java.util.List;
import misql.Funciones.As;

/**
 *
 * @author jesue
 */
public class SubTabla implements Tabla {

    private final List<Columna> cols=new ArrayList();
    private Join joins;
    private Condicion condicion;
    private Agrupacion agrupaciones;
    private Having having;
    private Tabla tabla;
    private String id;
    private final List<Columna> ref_cols = new ArrayList();

    public SubTabla(Tabla tabla, List<Columna> cols, Join joins, Condicion condicion, Agrupacion agrupaciones) {
        buildTabla(tabla, cols, joins, condicion, agrupaciones,null);
    }

    public SubTabla(Tabla tabla, List<Columna> cols, Join joins, Condicion condicion, Agrupacion agrupaciones,Having having) {
        buildTabla(tabla, cols, joins, condicion, agrupaciones,having);
    }
    private void buildTabla( Tabla tabla, List<Columna> cols, Join joins, Condicion condicion, Agrupacion agrupaciones,Having having) {
        this.id = "Sub"+tabla.getId();
        this.tabla = tabla;
        int cont=0;
        for (Columna c:cols) {
            this.cols.add(new SubColumna(new As(c,this.id+cont).toString()));
            this.ref_cols.add(new SubColumna(this,this.id+cont));
            cont++;
        }
        this.joins = joins;
        this.condicion = condicion;
        this.agrupaciones = agrupaciones;
        this.having=having;
    }

    public List<Columna> getColumnas() {
        return this.ref_cols;
    }

    private class SubColumna implements Columna {
        Tabla T;
        String columna;
        String as;
        
        SubColumna(String columna) {
            this.columna = columna;
            this.T = null;
        }
        SubColumna(Tabla tabla, String columna) {
            this.columna = columna;
            this.T = tabla;
        }
        @Override
        public Tabla T() {
            return this.T;
        }

        @Override
        public String toString() {
            return columna;
        }
        
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String txt = SentenciasSQL.buildSelection(tabla, cols, joins, condicion, agrupaciones,having);
        return String.format("(%s)", txt);
    }
}
