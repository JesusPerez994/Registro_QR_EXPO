package misql;

public class TablaPersonalizada implements Tabla{

    Tabla tabla;
    String sufijo="2";

    public TablaPersonalizada(Tabla tabla) {
        this.tabla = tabla;
    }
    public TablaPersonalizada(Tabla tabla, String sufijo) {
        this.tabla = tabla;
        this.sufijo = sufijo;
    }
    @Override
    public String getId() {
        return tabla.getId()+"_"+sufijo;
    }
    @Override
    public String toString() {
        return tabla.toString();
    }
}
