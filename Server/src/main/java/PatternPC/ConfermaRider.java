package PatternPC;

import Model.Rider;
import Model.Ristorante;

import java.util.LinkedList;

public class ConfermaRider {
    private LinkedList<Rider> riderDisponibili;
    private static ConfermaRider istanza = null;

    private ConfermaRider(){
        riderDisponibili = new LinkedList<>();
    }

    public static ConfermaRider getIstanza(){
        if(istanza == null)
            istanza = new ConfermaRider();
        return istanza;
    }

    public synchronized boolean produceRider (Rider r) throws InterruptedException {
        while(riderDisponibili.size() >= 10){
            wait();
        }

        riderDisponibili.add(r);
        notifyAll();

        return true;
    }

    public synchronized Rider consumaRider () throws InterruptedException {
        while (riderDisponibili.isEmpty()) {
            wait();
        }

        System.out.println("Sto controllando gli ordini da eseguire CONSUMA RIDER");
        Rider r = riderDisponibili.peek();
        notifyAll();
        return r;
    }

    public void visualizzaLista(){
        System.out.println(riderDisponibili);
    }

}
