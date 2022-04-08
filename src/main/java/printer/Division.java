package printer;

import java.awt.*;

public class Division extends Columna{
    public Division() {
        super("-");
    }
    public void buildDivision(int char_limit){
//        setTexto("-".repeat(char_limit));
        String guiones="";
        for (int i = 0; i < char_limit; i++) {
            guiones+="-";
        }
        setTexto(guiones);
    }
}
