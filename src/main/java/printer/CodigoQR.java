package printer;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CodigoQR extends Imagen{

    public CodigoQR(String ruta) {
        super(ruta);
    }

    public CodigoQR(BufferedImage image) {
        super(image);
    }

    @Override
    public int imprimir(Graphics g, int y, int char_limit, int p_width, FontMetrics metrics) {
        super.setAlineado(Alineado.Derecha);
        super.imprimir(g, 20, char_limit, p_width, metrics);
        return y;
    }
}