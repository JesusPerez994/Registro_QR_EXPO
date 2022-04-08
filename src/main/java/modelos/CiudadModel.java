package modelos;

public class CiudadModel {
    int idCiudad;
    int idEstado;
    String nombre;

    public CiudadModel(Object [] res) {
        this.setIdCiudad((Integer) res[0]);
        this.setIdEstado((Integer) res[1]);
        this.setNombre((String) res[2]);
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
