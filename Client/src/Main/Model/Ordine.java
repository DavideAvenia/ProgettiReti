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

    public Cliente getCliente(){
        return this.cliente;
    }

    public List<String> getProdotti() {
        return this.prodotti;
    }

    public Ristorante getRistorante(){return this.ristorante;}
}
