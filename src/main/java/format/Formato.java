/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package format;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

/**
 *
 * @author jesue
 */
public class Formato implements UnaryOperator<Change> {

    //Expresiones regulares
    private static final String NUMEROS_POSITIVOS = "(([\\d]+\\.)?[\\d]*)?";
    private static final String NUMEROS = "-?" + NUMEROS_POSITIVOS;
    private static final String NUMEROS_ENTEROS_POSITIVOS = "([0-9][\\d]*)?";
    private static final String NUMEROS_ENTEROS = "-?" + NUMEROS_ENTEROS_POSITIVOS;
    private static final String NUMEROS_Y_LETRAS = "([\\dA-ZÑÁÉÍÓÚ. ]*)?";
    private static final String LETRAS = "([A-ZÑÁÉÍÓÚ. ]*)?";
    private static final String TODO = ".*";
    
    public enum Type {
        CODIGO(new Formato(Formato.NUMEROS_Y_LETRAS, 20),String.class),
        CANTIDAD(new Formato(Formato.NUMEROS_ENTEROS_POSITIVOS,3),Integer.class),
        TEXTO(new Formato(Formato.TODO, 45),String.class),
        TEXTO_LARGO(new Formato(Formato.TODO),String.class),
        PORCENTAJE(new Formato(Formato.NUMEROS_ENTEROS_POSITIVOS, 3),Integer.class),
        DINERO(new Formato(Formato.NUMEROS_POSITIVOS, 10),BigDecimal.class),
        ENTERO(new Formato(Formato.NUMEROS_ENTEROS,6),Integer.class),
        ENTERO_POSITIVO(new Formato(Formato.NUMEROS_ENTEROS_POSITIVOS, 6),Integer.class),
        TELEFONO(new Formato(Formato.NUMEROS_ENTEROS_POSITIVOS,15),String.class),
        CORREO(new Formato(null),String.class);
        private final Formato f;
        private final Class clase;
        Type(Formato f, Class clase){
            this.f=f;
            this.clase=clase;
        }
        public Formato getFormato(){
            return this.f;
        }
        public Class getClase(){
            return this.clase;
        }
    }
    
    public static void setFormato(TextField tf, Type formato){
        tf.setTextFormatter(new TextFormatter(formato.getFormato()));
    }
    private final String expresion;
    private int limite = 60;

    public Formato(String expresion) {
        this.expresion = expresion;
    }

    public Formato(String exprecion, int limite) {
        this.expresion = exprecion;
        this.limite = limite;
    }

    @Override
    public Change apply(Change change) {
        String newText = change.getControlNewText().toUpperCase();
        if (newText.length() <= limite) {
            if (expresion!=null) {
                if (newText.matches(expresion)) {
                    change.setText(change.getText().toUpperCase());
                    return change;
                }
            }else{
                return change;
            }
        }
        return null;
    }
}
