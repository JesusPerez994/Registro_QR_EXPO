package printer;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Columna {
    String texto;

    public Columna(String texto) {
        this.texto = texto;
    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void imprimir(Graphics g,int x,int y){
        g.drawString(getTexto(),x,y);
        System.out.println(getTexto()+" "+y);
    }
    public void imprimirMuestra(GraphicsContext g, int x, int y){
        g.fillText(getTexto(),x,y);
        System.out.println(getTexto()+" "+y);
    }
}
