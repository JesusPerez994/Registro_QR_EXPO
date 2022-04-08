package identidades;

import identity.IdentidadSimple;
import misql.Columna;
import misql.Tabla;
import misql.Tablas;

import java.util.Arrays;
import java.util.List;

public class Clasificacion extends IdentidadSimple {
    public enum C implements Columna {
        id_clasificacion, nombre;
        Tabla T = Tablas.clasificacion;

        @Override
        public Tabla T() {
            return this.T;
        }
    }
    public enum Clasificaciones {
        Comprador(1,"COMPRADOR"),
        Proveedor(2,"PROVEEDOR"),
        Expositor(3,"EXPOSITOR"),
        Fabricante(4,"FABRICANTE"),
        Patrocinador(5,"PATROCINADOR"),
        InvitadoEspecial(6,"INVITADO ESPECIAL");
        int id;
        String nombre;

        Clasificaciones(int id,String nombre){
            this.id=id;
            this.nombre=nombre;
        }
        public int getId(){
            return this.id;
        }
        public String getNombre(){
            return this.nombre;
        }

        public List<Clasificaciones> getAll(){
            return Arrays.asList(Comprador,Proveedor,Expositor,Fabricante,Patrocinador,InvitadoEspecial);
        }
    }

    private static Clasificacion clasificacion;

    public static Clasificacion getInstance(){
        return clasificacion==null?new Clasificacion(Visitante.C.id_clasificacion,C.id_clasificacion,C.nombre):clasificacion;
    }
    public Clasificacion(Columna C_id_padre, Columna C_id, Columna C_nombre) {
        super(C_id_padre, C_id, C_nombre);
    }
}
