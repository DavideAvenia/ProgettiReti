package PatternPC;

import Model.Rider;

import java.util.LinkedList;

public class GestioneRider {

    private LinkedList<Rider> riderInviati;
    private static GestioneRider istanza = null;

    private GestioneRider(){
        riderInviati = new LinkedList<>();
    }

    public static GestioneRider getIstanza(){
        if(istanza == null)
            istanza = new GestioneRider();
        return istanza;
    }

    public synchronized boolean produceRiderInviati (Rider r) throws InterruptedException {
        while(riderInviati.size() >= 10){
            wait();
        }

        riderInviati.add(r);
        notifyAll();

        return true;
    }

    public synchronized Rider consumaRiderInviati (Rider r) throws InterruptedException {
        while (riderInviati.isEmpty()) {
            wait();
        }

        System.out.println("Sto controllando gli ordini da eseguire CONSUMA RIDER");
        Rider riderDaInviare = riderInviati.remove(riderInviati.indexOf(r));
        notifyAll();
        return riderDaInviare;
    }

    public void visualizzaLista(){
        System.out.println(riderInviati);
    }

    public LinkedList<Rider> getRiderInviati() {
        return riderInviati;
    }
}
