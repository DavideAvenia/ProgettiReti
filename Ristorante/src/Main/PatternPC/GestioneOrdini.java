package PatternPC;

import Model.Ordine;

import java.util.LinkedList;

public class GestioneOrdini {

    private LinkedList<Ordine> listaOrdini;
    private static GestioneOrdini istanza = null;

    private GestioneOrdini(){
        listaOrdini = new LinkedList<>();
    }

    public static GestioneOrdini getIstanza() {
        if(istanza == null){
            istanza = new GestioneOrdini();
        }
        return istanza;
    }

    public synchronized boolean produceOrdine(Ordine o) throws InterruptedException {
        while(listaOrdini.size() >= 10){
            wait();
        }

        boolean flag = listaOrdini.add(o);
        notifyAll();
        return flag;
    }

    public synchronized Ordine consumaOrdine() throws InterruptedException {
        while(listaOrdini.isEmpty()){
            wait();
        }

        Ordine o = listaOrdini.peek();
        notifyAll();
        return o;
    }
}
