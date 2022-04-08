package printer;

public class Header {
    Alineado alineado;
    int tam_porc;

    public Header(int tam_porc){
        this.tam_porc=tam_porc;
    }
    public Header(Alineado alineado, int tam_porc) {
        this.alineado = alineado;
        this.tam_porc = tam_porc;
    }
    public Alineado getAlineado() {
        return alineado;
    }

    public void setAlineado(Alineado alineado) {
        this.alineado = alineado;
    }

    public int getTam_porc() {
        return tam_porc;
    }

    public void setTam_porc(int tam_porc) {
        this.tam_porc = tam_porc;
    }
}
