/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author jesue
 */
public class Join {

    public enum Type {
        JOIN("JOIN"),
        LEFT_JOIN("LEFT JOIN"),
        RIGHT_JOIN("RIGHT JOIN");
        private final String join;

        Type(String join) {
            this.join = join;
        }

        @Override
        public String toString() {
            return this.join;
        }
    }
    private final Columna c_to_join;
    private final LinkedHashMap<Columna,Columna> columnas_to_join;
    private final Tabla tabla;
    private final Columna c_base;
    private final Type join_type;
    private Join jn;

    public Join(Columna c_to_join, Columna c_base, Type join) {
        this.c_base = c_base;
        this.c_to_join = c_to_join;
        this.tabla=c_to_join.T();
        this.columnas_to_join=null;
        this.join_type = join;
    }
    public Join(Tabla tabla,LinkedHashMap<Columna,Columna>columnas, Type join) {
        this.c_base = null;
        this.c_to_join=null;
        this.tabla=tabla;
        this.columnas_to_join=columnas;
        this.join_type = join;
    }
    public Join also(Columna c_to_join, Columna c_base, Type join) {
        return also(new Join(c_to_join,c_base,join));
    }
    public Join also(Tabla tabla,LinkedHashMap<Columna,Columna>columnas, Type join) {
        return also(new Join(tabla,columnas,join));
    }
    public Join also(Join jn) {
        addJoin(jn);
        return this;
    }
    private void addJoin(Join jn){
        if (this.jn == null) {
            this.jn = jn;
        } else {
            this.jn.addJoin(jn);
        }
    }

    @Override
    public String toString() {
        String txt = getStringJoin();
        if (jn == null) {
            return txt;
        }
        return String.format("%s %s", txt, jn);
    }

    private String getStringJoin(){
        if (columnas_to_join==null) {
            return String.format("%s %s ON %s = %s",
                    join_type.toString(),
                    tabla.withId(),
                    c_base.withTablaId(),
                    c_to_join.withTablaId());
        }else{
            String condicion = columnas_to_join.entrySet().stream().map(set->{
                return String.format("%s = %s",set.getKey().withTablaId(),set.getValue().withTablaId());
            }).collect(Collectors.joining(" AND "));
            return String.format("%s %s ON (%s)",join_type.toString(),tabla.withId(),condicion);
        }
    }
}