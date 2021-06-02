package PatternPC;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import java.util.HashMap;

public class OrdiniDaEseguire {
    private HashMap<Ristorante,Ordine> ordiniDaEseguire;
    private HashMap<Rider, Ordine> ordiniEseguiti;
    private static OrdiniDaEseguire istanza = null;

    private OrdiniDaEseguire(){
        ordiniDaEseguire = new HashMap<>();
        ordiniEseguiti = new HashMap<>();
    }

    public static OrdiniDaEseguire getIstanza(){
        if(istanza == null)
            istanza = new OrdiniDaEseguire();
        return istanza;
    }

    public synchronized boolean produceOrdine (Ordine o) throws InterruptedException {
        while(ordiniDaEseguire.size() >= 10){
            wait();
        }

        ordiniDaEseguire.put(o.getRistorante(), o);
        notifyAll();

        return true;
    }

    public synchronized Ordine consumaOrdine (Ristorante ristorante) throws InterruptedException {
        while (ordiniDaEseguire.isEmpty() || ordiniDaEseguire.containsKey(ristorante)) {
            wait();
        }

        System.out.println("Sto controllando gli ordini da eseguire CONSUMA ORDINE");
        Ordine ordineDaImportare = ordiniDaEseguire.get(ristorante);
        notifyAll();
        return ordineDaImportare;
    }

    public void visualizzaListaOrdiniDaEseguire(){
        System.out.println(ordiniDaEseguire);
    }

    public synchronized boolean produceOrdineEseguiti(Rider key, Ordine value) throws InterruptedException {
        while(ordiniEseguiti.size() >= 10){
            wait();
        }

        ordiniEseguiti.put(key,value);
        notifyAll();

        return true;
    }

    public synchronized boolean consumaOrdineEseguiti(Rider r) throws InterruptedException {
        while(ordiniEseguiti.isEmpty()){
            wait();
        }

        if(ordiniEseguiti.containsValue(r))
        notifyAll();

        return true;
    }

    public synchronized boolean controllaPresenzaOrdineEseguito(Rider r) throws InterruptedException {
        while(!ordiniEseguiti.containsKey(r)){
            wait();
        }
        notifyAll();
        return ordiniEseguiti.containsKey(r);
    }
}
