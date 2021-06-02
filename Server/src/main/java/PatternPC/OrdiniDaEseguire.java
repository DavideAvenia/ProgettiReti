package PatternPC;

import Model.Ordine;
import Model.Ristorante;

import java.util.HashMap;
import java.util.LinkedList;

public class OrdiniDaEseguire {
    private HashMap<Ristorante,Ordine> ordiniDaEseguire;
    private static OrdiniDaEseguire istanza = null;

    private OrdiniDaEseguire(){
        ordiniDaEseguire = new HashMap<>();
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

    public void visualizzaLista(){
        System.out.println(ordiniDaEseguire);
    }

}
