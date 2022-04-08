package printer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Logo extends Imagen{
    static String ruta="/img/Afamo(50).png";
    public Logo() {
        super(ruta);
    }

    public Logo(BufferedImage image) {
        super(image);
    }

    @Override
    public int imprimir(Graphics g, int y, int char_limit, int p_width, FontMetrics metrics) {
        super.setAlineado(Alineado.Derecha);
        super.imprimir(g, 110,-20, char_limit, p_width, metrics);
        return y;
    }
}
