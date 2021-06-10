package Model;

import java.io.Serializable;
import java.util.List;

public class Ordine implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Cliente cliente;
    private List<String> prodotti;
    private Ristorante ristorante;

    public Ordine(){

    }

    public Ordine(Cliente cliente, List<String> prodotti, Ristorante ristorante){
        this.cliente = cliente;
        this.prodotti = prodotti;
        this.ristorante = ristorante;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Ordine))
            return false;

        Ordine o = (Ordine) other;
        return this.getCliente().getIdCliente().equals(o.getCliente().getIdCliente()) &&
                this.getRistorante().getIdRistorante().equals(o.getRistorante().getIdRistorante());
    }

    @Override
    public int hashCode() {
        return cliente.getIdCliente().hashCode() ^ ristorante.getIdRistorante().hashCode();
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public List<String> getProdotti() {
        return this.prodotti;
    }

    public Ristorante getRistorante(){return this.ristorante;}
}
