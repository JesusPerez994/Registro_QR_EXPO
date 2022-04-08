package printer;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.List;

public class Impresion {

    List<Parrafo> parrafos =new ArrayList<>();
    FontMetrics metrics;
    PageFormat pf;

    public List<Parrafo> getParrafos() {
        return parrafos;
    }
    public void setParrafos(List<Parrafo> parrafos) {
        this.parrafos = parrafos;
    }
    public FontMetrics getMetrics() {
        return metrics;
    }
    public void setMetrics(FontMetrics metrics) {
        this.metrics = metrics;
    }
    public PageFormat getPf() {
        return pf;
    }
    public void setPf(PageFormat pf) {
        this.pf = pf;
    }

    public void addDivision(){
        Parrafo parrafo = new Parrafo(Alineado.Centrado);
        parrafo.addDivision();
        getParrafos().add(parrafo);
    }
    private void imprimirBuilder(Graphics g,PageFormat pf){
        setPf(pf);
    }
    public void imprimir(Graphics g, PageFormat pf){
        imprimirBuilder(g,pf);
        int y=40; //OFFSET
        for (Parrafo parrafo: getParrafos()){
            g.setFont(parrafo.getFont());
            setMetrics(g.getFontMetrics());
            y=parrafo.imprimir(g,y,getCharLimit(),getPageWidth(),getMetrics());
        }
    }
    public void imprimirMuestra(GraphicsContext g,int y,int p_width,int char_limit,int tam_char,int esp){
        g.setFont( new javafx.scene.text.Font("Courier New", 10));
        for (Parrafo parrafo: getParrafos()){
            y=parrafo.imprimirMuestra(g,y,char_limit,p_width,tam_char,esp);
        }
    }

    private int getCharWidth(){
        if (getMetrics().getFont().getName().equals(Font.MONOSPACED))
            return getMetrics().stringWidth(" ");

        return getMetrics().stringWidth("  l");
    }
    private int getCharLimit(){
        return getPageWidth() / getCharWidth();
    }
    private int getPageWidth(){
        return (int) getPf().getImageableWidth();
    }
}
