package identity;

import misql.Columna;
import misql.Tabla;

public class SuperIdentidadIdDoble implements IdentidadIdDoble {

    Columna C_id1,C_id2;
    public SuperIdentidadIdDoble(Columna C_id1,Columna C_id2) {
        this.C_id1=C_id1;
        this.C_id2=C_id2;
    }
    @Override
    public Columna getColumnaId1() {
        return this.C_id1;
    }

    @Override
    public Columna getColumnaId2() {
        return this.C_id2;
    }
}
