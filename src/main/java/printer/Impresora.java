/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printer;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import data.Preferencias;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import json.JSONData;
import org.json.JSONException;

/**
 *
 * @author jesue
 */
public interface Impresora {

    
    public static PrintService[] getPrintServices() {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        return PrintServiceLookup.lookupPrintServices(flavor, pras);
    }

    public static List<String> getPrintServicesAsStrings() {
        List<String> lista;
        lista = Stream.of(getPrintServices()).map(p -> p.getName()).collect(Collectors.toList());
        return lista;
    }
     static PrintService findPrintService(String printerName,
            PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null;
    }
    public static void imprimirCodigoQR(JSONData qr, String alias){
        try {
            System.out.println(qr.toString());
            BitMatrix matrix = new MultiFormatWriter().encode(qr.toJSON().toString(), BarcodeFormat.QR_CODE,100,100);
//            MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get("\\CodigoQR.jpg"));
            CodigoQR codigoQR = new CodigoQR(MatrixToImageWriter.toBufferedImage(matrix).getSubimage(9,9,88,88));
            Impresora.imprimir(Preferencias.get(Preferencias.Key.IMPRESORA),new Etiqueta(alias,qr.getEmpresa(),codigoQR));
        } catch (PrinterException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/ catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void imprimir(String impresora, Impresion impresion) throws PrinterException {
        NuevoPrintable printable=new NuevoPrintable(impresion);
        PrinterJob printerJob=PrinterJob.getPrinterJob();

        PageFormat pf = printerJob.defaultPage();
        Paper paper = pf.getPaper();
        double width = 2.3125d * 72d;
        double height = 4d * 72d;
        double margin = 0d * 72d;
        paper.setSize(width, height);
        paper.setImageableArea(
                0,
                margin,
                width - (margin * 2),
                height - (margin * 2));

        pf.setPaper(paper);
        pf.setOrientation(PageFormat.LANDSCAPE);
        printerJob.setPrintable(printable,pf);
        printerJob.setPrintService(findPrintService(impresora, getPrintServices()));
        //PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        //aset.add(OrientationRequested.LANDSCAPE);
        //printerJob.print(aset);
        printerJob.print();
    }

}
