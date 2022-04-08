package printer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Imagen extends Parrafo{

    String ruta;
    BufferedImage bufferedImage;

    public Imagen(String ruta){this.ruta=ruta;}
    public Imagen(BufferedImage image){this.bufferedImage=image;}

    Alineado alineado;

    public Imagen setAlineado(Alineado alineado) {
        this.alineado = alineado;
        return this;
    }

    @Override
    public int imprimir(Graphics g, int y, int char_limit, int p_width, FontMetrics metrics) {
        return imprimir(g,y,0,char_limit,p_width,metrics);
    }

    public int imprimir(Graphics g, int y,int xOffset, int char_limit, int p_width, FontMetrics metrics) {
        BufferedImage imagen = bufferedImage;
        if (imagen == null) {
            if (ruta==null){
                return 0;
            }else{
                try {
                    imagen = ImageIO.read(getClass().getResource(ruta));
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        int x=0;
        if (this.alineado!=null) {
            switch (this.alineado) {
                case Centrado : {
                    x = (p_width - imagen.getWidth()) / 2;
                    break;
                }
                case Derecha : {
                    x = p_width - imagen.getWidth();
                    break;
                }
            }
        }
        x=x+xOffset;
        g.drawImage(imagen, x, y, null);
        y+=imagen.getHeight()+(getEspaciado(metrics)/2);
        //y+=imagen.getHeight();
        return y;
    }

    @Override
    public int imprimirMuestra(GraphicsContext g, int y, int char_limit, int p_width, int tam_char, int esp) {
        Image imagen = new Image(bufferedImage.toString());
        int imageHeight = (int) imagen.getHeight();
        g.drawImage(imagen, (p_width - imagen.getWidth()) / 2, esp);
        y += imageHeight + esp;
        return y;
    }
}