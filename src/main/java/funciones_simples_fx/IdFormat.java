package funciones_simples_fx;

public class IdFormat {
    private static String prefix="AF";

    public static String getIdFromText(String text){
        String id="";
        try{
            id = text.split(prefix)[1];
        }catch (ArrayIndexOutOfBoundsException e){

        }
        return id;
    }

    public static String buildId(int id){
        return prefix+id;
    }

}
