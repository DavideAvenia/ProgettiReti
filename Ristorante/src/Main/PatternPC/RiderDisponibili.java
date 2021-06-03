package PatternPC;

import Model.Rider;

import java.util.LinkedList;

public class RiderDisponibili {
    private LinkedList<Rider> riderDisponibili;
    private static RiderDisponibili istanza = null;

    private RiderDisponibili(){
        riderDisponibili = new LinkedList<>();
    }

    public static RiderDisponibili getIstanza(){
        if(istanza == null)
            istanza = new RiderDisponibili();
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

    public LinkedList<Rider> getRiderDisponibili() {
        return riderDisponibili;
    }
}
