package Model;

import java.util.ArrayList;
import java.util.List;

public class Ordine {
    private Cliente cliente;
    private List<String> prodotti;

    public Ordine(){

    }

    public Ordine(Cliente cliente, List<String> prodotti){
        this.cliente = cliente;
        this.prodotti = prodotti;
    }
}
