package PatternPC;

import Model.Ordine;

import java.util.LinkedList;

/*
Questa classe si occupa della gestione della lista degli ordini,
che vengono prodotti dai client e consumati dai ristoranti che li
inviano ai Rider.
Anche in questa classe è stato implementato il pattern singleton,
quindi il costruttore è privato, e viene richiamato nella funzione
'getIstanza'. La lista degli ordini è costruita come LinkedList.
 */
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

    /*
    La funzione ha il compito di inserire l'ordine, specificato nella firma, nella
    lista degli Ordini. Se la lista è piena, cioè contiene 10 o più elementi allora
    rimane in attesa, quando la lista non è piena, invece, l'ordine può essere
    aggiunto alla lista degli ordini. infine vengono notificati tutti i thread.
    La funzione ritorna un flag boolean.
     */
    public synchronized boolean produceOrdine(Ordine o) throws InterruptedException {
        while(listaOrdini.size() >= 10){
            wait();
        }

        boolean flag = listaOrdini.add(o);
        System.out.println("HO PRODOTTO UN ORDINE AL "+ o.getRistorante() + " " + o.getCliente());
        notifyAll();
        return flag;
    }

    /*
    La funzione è il lato consumatore del pattern produttore-consumatore.
    Attende finchè la lista è vuota, dopo di che prende l'ordine in testa
    alla lista degli ordini ma senza rimuoverlo. Infine invia una notifica
    a tutti gli altri thread.
    La funzione ritorna l'ordine che è stato preso in testa alla lista.
     */
    public synchronized Ordine consumaOrdine() throws InterruptedException {
        while(listaOrdini.isEmpty()){
            wait();
        }

        Ordine o = listaOrdini.peek();
        notifyAll();
        return o;
    }
}
