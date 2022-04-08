package misql;

public class ColumnaPersonalizada implements Columna {
    TablaPersonalizada tabla;
    Columna columna;
    public ColumnaPersonalizada(TablaPersonalizada tabla,Columna columna) {
        this.tabla = tabla;
        this.columna = columna;
    }
    @Override
    public Tabla T() {
        return tabla;
    }

    @Override
    public String toString() {
        return columna.toString();
    }
}
