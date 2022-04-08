/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

import data.Fecha;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jesue
 */
public class Condicion {

    public enum Type {
        IN("%s IN (%s)"),
        NOT_IN("%s NOT IN (%s)"),
        MAYOR("%s > %s"),
        MENOR("%s < %s"),
        IGUAL("%s = %s"),
        BETWEEN(" %s BETWEEN %s");
        String exp;

        Type(String exp) {
            this.exp = exp;
        }

        @Override
        public String toString() {
            return this.exp;
        }

    }

    private enum Operador {
        AND, OR
    }
    private final Columna col;
    private final Columna col2;
    private final Type tipo;
    private final List<Object> valores;
    private Condicion cond;
    private Operador opr;
    private Condicion cond_int;
    private Operador opr_int;
    public Condicion(Columna col, Type tipo, Object... valores) {
        this.col = col;
        this.tipo = tipo;
        this.valores = Arrays.asList(valores);
        col2 = null;
    }
    public Condicion(Columna col, Type tipo, Columna col2) {
        this.col = col;
        this.tipo = tipo;
        this.col2 = col2;
        valores = null;
    }
    public Condicion(Columna col, Type tipo, List valores) {
        this.col = col;
        this.tipo = tipo;
        this.valores = valores;
        col2 = null;
    }
    public Condicion(Columna col){
        this.col=col;
        this.tipo=null;
        this.valores=null;
        this.col2=null;
    }
    public Condicion AND(Condicion cond) {
        addCondicion(cond, Operador.AND);
        return this;
    }
    public Condicion OR(Condicion cond) {
        addCondicion(cond, Operador.OR);
        return this;
    }
    public Condicion interAND(Condicion cond){
        addCondicionInterior(cond, Operador.AND);
        return this;
    }
    public Condicion interOR(Condicion cond){
        addCondicionInterior(cond, Operador.OR);
        return this;
    }
    private void addCondicion(Condicion cond, Operador opr) {
        if (this.cond == null) {
            this.cond = cond;
            this.opr = opr;
        } else {
            this.cond.addCondicion(cond, opr);
        }
    }
    private void addCondicionInterior(Condicion cond, Operador opr) {
        if (this.cond_int == null) {
            this.cond_int = cond;
            this.opr_int = opr;
        } else {
            this.cond_int.addCondicion(cond, opr);
        }
    }
    

    @Override
    public String toString() {
        String condicion;
        if (tipo == null){
            condicion = col.withTablaId();
        }else if (valores == null) {
            condicion = String.format(tipo.toString(), col.withTablaId(), col2.withTablaId());
        } else {
            String delimiter=tipo.equals(Type.BETWEEN)?"AND":",";
            condicion = String.format(tipo.toString(), col.withTablaId(),
                    valores.stream().map(m -> {
                        if (m instanceof String){
                            return String.format("'%s'",m.toString());
                        }else if(m instanceof Date){
                            return String.format("'%s'",Fecha.formatoAnoMesDiaTiempo(((Date)m)));
                        }
                        return m.toString();
                    }).collect(Collectors.joining(delimiter)));
        }
        if (cond_int!=null) {
            condicion+= String.format(" %s (%s)", opr_int, cond_int);
        }
        if (cond != null) {
            return String.format("%s %s %s",condicion, opr, cond);
        }
        return condicion;
    }

}
