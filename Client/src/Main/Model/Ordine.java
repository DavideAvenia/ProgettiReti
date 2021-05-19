package Model;

import java.io.Serializable;
import java.util.List;

public class Ordine implements Serializable {
    private Cliente cliente;
    private List<String> prodotti;

    public Ordine(){

    }

    public Ordine(Cliente cliente, List<String> prodotti){
        this.cliente = cliente;
        this.prodotti = prodotti;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public List<String> getProdotti() {
        return prodotti;
    }
}
