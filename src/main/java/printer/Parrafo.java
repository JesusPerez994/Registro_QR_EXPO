package printer;

import com.sun.javafx.font.Metrics;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

import java.awt.*;
import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Parrafo {
    private List<Header>headers=new ArrayList<>();
    private List<Renglon>renglones=new ArrayList<>();
    Font font = new Font(Font.SERIF, Font.BOLD, 20);

    public Parrafo(){
        this.headers.add(new Header(null,100));
    }
    public Parrafo(int tam){
        this.headers.add(new Header(null,tam));
    }
    public Parrafo(Alineado alineado){
        this.headers.add(new Header(alineado,100));
    }
    public Parrafo(Alineado alineado,int tam){
        this.headers.add(new Header(alineado,tam));
    }
    public Parrafo(List<Header> headers) {
        this.headers = headers;
    }
    public void setHeaders(List<Header>headers){
        this.headers=headers;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public List<Header> getHeaders() {
        return headers;
    }
    public List<Renglon> getRenglones() {
        return renglones;
    }
    public void setRenglones(List<Renglon> renglones) {
        this.renglones = renglones;
    }
    public void addRenglon(String txt){
        getRenglones().add(new Renglon(txt));
    }
    public void addDivision(){
        getRenglones().add(new Renglon(new Division()));
    }
    public Alineado getAlinieadoDeColumna(int indice_col){
        return getHeaders().get(indice_col).getAlineado();
    }
    public int getPorcDeColumna(int indice_col){
        return getHeaders().get(indice_col).getTam_porc();
    }

    public int getCharLimitDeColumna(int indice_col, int char_limit){
        return char_limit*getPorcDeColumna(indice_col)/100;
    }
    public int getAnchodeColumna(int indice_col,int p_width){
        return p_width*getPorcDeColumna(indice_col)/100;
    }
    public int getEspaciado(FontMetrics metrics){
        //return metrics.getHeight()+metrics.getLeading();
        return metrics.getHeight();
    }

    public int imprimir(Graphics g, int y, int char_limit, int p_width, FontMetrics metrics){
        for (int i = 0; i < getRenglones().size(); i++) {
            Renglon renglon=getRenglones().get(i);
            Renglon nuevo_renglon=new Renglon();
            int x_sum=0;
            for (int j = 0; j < renglon.getColumnas().size(); j++) {
                Columna columna = renglon.getColumnas().get(j);
                if (columna instanceof Division){
                    ((Division) columna).buildDivision(char_limit);
                }
                dobleFila(nuevo_renglon,j,columna,char_limit);
                int tam=metrics.stringWidth(columna.getTexto());
                int col_width=getAnchodeColumna(j,p_width);
                int x = x_sum;
                x_sum+=col_width;
                if (getAlinieadoDeColumna(j)!=null) {
                    switch (getAlinieadoDeColumna(j)) {
                        case Derecha:
                            x += col_width - tam;
                            break;
                        case Centrado:
                            x += (col_width - tam) / 2;
                            break;
                    }
                }
                columna.imprimir(g,x,y);
            }
            if (!nuevo_renglon.getColumnas().stream().allMatch(c->c.getTexto().equals(""))){
                getRenglones().add(i+1,nuevo_renglon);
            }
            y+=getEspaciado(metrics);
        }
        return y;
    }
    private void dobleFila(Renglon nuevo_renglon,int indice,Columna columna,int char_limit){
        int col_char_limit=getCharLimitDeColumna(indice,char_limit);
        if (columna.getTexto().length()>col_char_limit){
            Columna nueva_col=new Columna("");
            int sum=0;
            String txt_cortado="";
            columna.setTexto(columna.getTexto().replace(" "," ~"));
            for (String palabra:columna.getTexto().split("~")){
                sum += palabra.length();
                if (sum <= col_char_limit) {
                    txt_cortado += palabra;
                } else {
                    if (palabra.length()>col_char_limit){
                        //Si es la primera palabra
                        if (sum-palabra.length()==0){
                            String nvo_txt=columna.getTexto().substring(col_char_limit);
/*                            if (nvo_txt.isBlank()){
                                nvo_txt="";
                            }*/
                            nueva_col=new Columna(nvo_txt);
                            txt_cortado=columna.getTexto().substring(0,col_char_limit);
                            break;
                        }else{
                            nueva_col.setTexto(nueva_col.getTexto() + palabra);
                            //txt_cortado += palabra.substring(0,char_limit);
                        }
                    }else{
                        nueva_col.setTexto(nueva_col.getTexto() + palabra);
                    }
                }
            }
            columna.setTexto(txt_cortado);
            nuevo_renglon.getColumnas().add(nueva_col);
        }else{
            nuevo_renglon.getColumnas().add(new Columna(""));
        }
    }
    public int imprimirMuestra(GraphicsContext g, int y, int char_limit, int p_width,int tam_char,int esp){
        for (int i = 0; i < getRenglones().size(); i++) {
            Renglon renglon=getRenglones().get(i);
            Renglon nuevo_renglon=new Renglon();
            int x_sum=0;
            for (int j = 0; j < renglon.getColumnas().size(); j++) {
                Columna columna = renglon.getColumnas().get(j);
                if (columna instanceof Division){
                    ((Division) columna).buildDivision(char_limit);
                }
                dobleFila(nuevo_renglon,j,columna,char_limit);
                int tam= columna.getTexto().length() * tam_char;
                int col_width=getAnchodeColumna(j,p_width);
                int x = x_sum;
                x_sum+=col_width;
                if (getAlinieadoDeColumna(j)!=null) {
                    switch (getAlinieadoDeColumna(j)) {
                        case Derecha:
                            x += col_width - tam;
                            break;
                        case Centrado:
                            x += (col_width - tam) / 2;
                            break;
                    }
                }
                columna.imprimirMuestra(g,x,y);
            }
            if (!nuevo_renglon.getColumnas().stream().allMatch(c->c.getTexto().equals(""))){
                getRenglones().add(i+1,nuevo_renglon);
            }
            y+=esp;
        }
        return y;
    }
}
