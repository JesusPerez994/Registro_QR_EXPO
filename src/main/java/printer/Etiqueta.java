package printer;


import java.awt.*;
import java.awt.print.PageFormat;
import java.util.Arrays;
import java.util.List;

public class Etiqueta extends Impresion{

    String nombre;
    String empresa;
    Imagen qrCode;
    public Etiqueta(String nombre) {
        this.nombre=nombre;
    }
    public Etiqueta(String nombre,String empresa, CodigoQR qrCode){
        this.nombre=nombre;
        this.empresa=empresa;
        this.qrCode=qrCode;
    }
    private void buildEtiqueta(){
        getParrafos().clear();
        if (qrCode!=null){
            getParrafos().add(qrCode.setAlineado(Alineado.Derecha));
        }
        getParrafos().add(new Logo());
        List<Header>headers=Arrays.asList(
                new Header(Alineado.Centrado,70),
                new Header(Alineado.Centrado,30)
        );
        Parrafo parrafoNombre = new Parrafo(headers);
        parrafoNombre.addRenglon(this.nombre);
        Parrafo parrafoEmpresa = new Parrafo(headers);
        parrafoEmpresa.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        parrafoEmpresa.addRenglon(this.empresa);
        getParrafos().add(parrafoNombre);
        getParrafos().add(parrafoEmpresa);
    }

    @Override
    public void imprimir(Graphics g, PageFormat pf) {
        buildEtiqueta();
        super.imprimir(g, pf);
    }
}
