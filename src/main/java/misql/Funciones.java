/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misql;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author jesue
 */
public class Funciones implements Columna {
    @Override
    public Tabla T() {
        return null;
    }

    public String objectToString(Object object){
        if (object instanceof String){
            return String.format("'%s'",object);
        }else if(object instanceof Columna){
            return ((Columna) object).withTablaId();
        }else{
            return object.toString();
        }
    }
    public static class Custom extends Funciones{
        String formato;
        Object[]datos;

        public Custom(String formato, Object... datos) {
            this.formato = formato;
            this.datos = datos;
        }
        @Override
        public String toString() {
            List<String> cast= Arrays.stream(datos).map(m->objectToString(m)).collect(Collectors.toList());
            return String.format(formato,cast.toArray());
        }
    }
    private static class Agregadas extends Funciones{
        enum Type{
            MAX,MIN,SUM,ISNULL,ISNOTNULL,COUNT;
        }
        Columna col;
        Type tipo;
        private Agregadas(Columna col,Type tipo){
            this.col=col;
            this.tipo=tipo;
        }
        @Override
        public String toString() {
            return String.format("%s(%s)", tipo,col.withTablaId());
        }
    }
    public static class Max extends Agregadas{
        public Max(Columna col) {
            super(col,Type.MAX);
        }
    }
    public static class Min extends Agregadas{
        public Min(Columna col) {
            super(col,Type.MIN);
        }
    }
    public static class Sum extends Agregadas{
        public Sum(Columna col) {
            super(col,Type.SUM);
        }
    }
    public static class IsNull extends Agregadas{
        public IsNull(Columna col){super(col,Type.ISNULL);}
    }
    public static class Count extends Agregadas{
        public Count(Columna col){super(col,Type.COUNT);}
    }
    public static class IsNotNull extends Agregadas{
        public IsNotNull(Columna col){super(col,Type.ISNOTNULL);}
        @Override
        public String toString() {
            return String.format("%s %s", col.withTablaId(),tipo);
        }
    }
    public static class Const extends Funciones{
        Object valor;
        public Const(Object valor){
            this.valor=valor;
        }
        @Override
        public String toString() {
            if (valor instanceof String || valor instanceof Enum){
                return String.format("'%s'",valor.toString());
            }else{
                return valor.toString();
            }
        }
    }
    public static class As extends Funciones{
        Columna col;
        String as;
        public As(Columna col,String as){
            this.col=col;
            this.as=as;
        }
        @Override
        public String toString() {
            return String.format("%s %s", col.withTablaId(),as);
        }
    }
    public static class Cast extends Funciones{
        Columna col;
        Object constante;
        Type tipo;
        public enum Type{
            SIGNED,UNSIGNED;
        }
        public Cast(Columna col,Type tipo){
            this.col=col;
            this.tipo=tipo;
        }
        public Cast(Object constante,Type tipo){
            this.constante=constante;
            this.tipo=tipo;
        }

        @Override
        public String toString() {
            String valor;
            if (col==null){
                valor= constante.toString();
            }else{
                valor=col.withTablaId();
            }
            return String.format("CAST(%s as %s)",valor,tipo);
        }
    }
    public static class Concat extends Funciones {
        Object[]val;
        public Concat(Object... val) {
            this.val=val;
        }
        @Override
        public String toString() {
            String res=Arrays.asList(val).stream().map(v->{
                if (v instanceof Columna) {
                    return ((Columna)v).withTablaId();
                }
                return String.format("'%s'", v.toString());
            }).collect(Collectors.joining(","));
            return String.format("CONCAT(%s)",res);
        }
    }

    public static class IfNull extends Funciones {
        String FORMATO="IFNULL(%s,%s)";
        Object res;
        Columna col;
        public IfNull(Columna col,Object res){
            this.col=col;
            this.res=res;
        }
        @Override
        public String toString() {
            return String.format(FORMATO,col.withTablaId(),objectToString(res));
        }
    }
    public static class If extends Funciones{
        String FORMATO="IF(%s,%s,%s)";
        Columna col;
        Object val1,val2;
        public If(Columna col, Object val1, Object val2) {
            this.col = col;
            this.val1 = val1;
            this.val2 = val2;
        }
        @Override
        public String toString() {
            return String.format(FORMATO,col.withTablaId(),objectToString(val1),objectToString(val2));
        }
    }

    private static class Operaciones extends Funciones{
        public enum Type{
            SUMAR("+"),RESTAR("-"),MULTIPLICAR("*"),DIVIDIR("/");
            String operador;
            Type(String operador){
                this.operador=operador;
            }
            @Override
            public String toString() {
                return operador;
            }
        }
        Type tipo;
        Columna[]columnas;
        private Operaciones(Type tipo,Columna... columna){
            this.tipo=tipo;
            columnas=columna;
        }
        @Override
        public String toString(){
            String res=Arrays.asList(columnas).stream().map(v->{
                return String.format("%s", v.withTablaId());
            }).collect(Collectors.joining(tipo.toString()));
            return String.format("(%s)",res);
        }
    }
    public static class Multiplicar extends Operaciones{
        public Multiplicar(Columna... columnas){
            super(Type.MULTIPLICAR,columnas);
        }
    }
    public static class Sumar extends Operaciones{
        public Sumar(Columna... columna) {
            super(Type.SUMAR, columna);
        }
    }
    public static class Restar extends Operaciones{
        public Restar(Columna... columna){
            super(Type.RESTAR,columna);
        }
    }
    public static class Dividir extends Operaciones{
        public Dividir(Columna... columna){
            super(Type.DIVIDIR,columna);
        }
    }
    public static class Encriptar extends Funciones {
        private String password;
        public Encriptar(String password){
            this.password =password;
        }
        @Override
        public String toString() {
            return String.format("AES_ENCRYPT('%s','Ej3sus')", password);
        }
    }
}
