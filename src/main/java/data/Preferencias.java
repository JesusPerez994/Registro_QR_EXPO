package data;

import java.util.prefs.Preferences;

public interface Preferencias {
    static final String RUTA="/com/codigoqrprogram";
    static final Preferences prefs= Preferences.userRoot().node(RUTA);
    public static enum Key {
        DB_ROUTE,DB_USER,DB_PASSWORD,IMPRESORA
    }
    public static void put(Key key, String value){
        prefs.put(key.toString(),value);
    }
    public static String get(Key key){
        return prefs.get(key.toString(),null);
    }
}
