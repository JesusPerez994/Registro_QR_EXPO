package printer;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Renglon {
    List<Columna> columnas=new ArrayList<>();
    public Renglon(){

    }
    public Renglon(String txt) {
        getColumnas().add(new Columna(txt));
    }
    public Renglon(Columna col){
        getColumnas().add(col);
    }
    public Renglon(List<Columna> columnas){
        setColumnas(columnas);
    }
    public List<Columna> getColumnas() {
        return columnas;
    }
    public void setColumnas(List<Columna> columnas) {
        this.columnas = columnas;
    }
}
