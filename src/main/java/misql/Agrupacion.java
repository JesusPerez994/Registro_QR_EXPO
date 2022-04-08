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
public class Agrupacion {
    public enum Type{
        GROUP_BY("GROUP BY %s"),
        ORDER_BY("ORDER BY %s"),
        ORDER_BY_DESC("ORDER BY %s DESC"),
        ORDER_BY_ASC("ORDER BY %s ASC"),
        HAVING("HAVING %s");
        String exp;
        Type(String exp){
            this.exp=exp;
        }
        @Override
        public String toString() {
            return this.exp;
        }
        
    }
    private final Type tipo;
    private final Columna col;
    private Agrupacion agpc;
    public Agrupacion(Type tipo,Columna col){
        this.tipo=tipo;
        this.col=col;
    }
    public Agrupacion also(Agrupacion agpc){
        addAgrupacion(agpc);
        return this;
    }
    private void addAgrupacion(Agrupacion agpc){
        if (this.agpc == null) {
            this.agpc = agpc;
        } else {
            this.agpc.addAgrupacion(agpc);
        }
    }
    @Override
    public String toString() {
        String texto=String.format(tipo.toString(),col.withTablaId());
        if (agpc==null) {
            return texto;
        }
        return String.format("%s %s", texto,agpc);
    }
    
}
