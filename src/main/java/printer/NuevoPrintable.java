package printer;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class NuevoPrintable implements Printable {
    Impresion impresion;

    public NuevoPrintable(Impresion impresion) {
        this.impresion = impresion;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex>0){
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        //g2d.translate(0, 0);
        this.impresion.imprimir(g,pf);
        return PAGE_EXISTS;
    }
}
