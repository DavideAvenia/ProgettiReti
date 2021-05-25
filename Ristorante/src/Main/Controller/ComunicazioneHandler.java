package Controller;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ComunicazioneHandler {

    public static class OrdineHandler{
        //gestione degli ordini che arrivano dal server

        BlockingQueue<Ordine> ordini = new LinkedBlockingQueue();

        public void produceOrdine(Ordine ordine) throws InterruptedException {
            synchronized (ordini) {
                // il produttore non può produrre se la linkedList è piena
                while(ordini.size() >= 10)
                    wait();

                ordini.put(ordine);
                System.out.println("Aggiunto il valore: al " + ordine.getRistorante().getNome() + " di " + ordine.getCliente().getCognome());
                ordini.notifyAll();
            }
        }

        public Ordine consumaOrdine() throws InterruptedException {
            Ordine ordineConsume = null;
            synchronized (ordini){
                //Il consumatore non può consumare quando la linkedList è vuota
                while(ordini.isEmpty())
                    wait();

                ordineConsume = ordini.take();
                System.out.println("Elaborato l'ordine: di " + ordineConsume.getCliente());

            }
            ordini.notifyAll();

            return ordineConsume;
        }
    }

    public static class ComunicazioneRiderHandler{
        BlockingQueue<Rider> riderDisponibili = new LinkedBlockingQueue();

        public void produceRider(Rider rider) throws InterruptedException {
            synchronized (riderDisponibili) {
                //Il produttore non può produrre quando ci sono 10 o più rider
                while(riderDisponibili.size() >= 10)
                    wait();

                riderDisponibili.put(rider);
                System.out.println("Il rider " + rider.getIdRider() + "di cognome " + rider.getCognome() + "è pronto");
                //Rilascia il monitor a tutti
                riderDisponibili.notifyAll();
            }
        }

        public Rider consumaRider() throws InterruptedException {
            Rider riderDaOccupare = null;
            //Simile a ordine da consumare

            synchronized (riderDisponibili){
                while(riderDisponibili.isEmpty())
                    wait();

                riderDaOccupare = riderDisponibili.take();
                System.out.println("Il rider " + riderDaOccupare.getIdRider() + "di cognome " + riderDaOccupare.getCognome() + "è stato rimosso");
                riderDisponibili.wait();
            }
            return riderDaOccupare;
        }

        public ArrayList<Rider> getRiderDisponibili(){return (ArrayList<Rider>) riderDisponibili;}
    }

    public static class RiderConfermatiHandler{
        BlockingQueue<Rider> riderConfermati = new LinkedBlockingQueue();

        public void produceRider(Rider rider) throws InterruptedException {
            synchronized (riderConfermati) {
                //Il produttore non può produrre quando ci sono 10 o più rider
                while(riderConfermati.size() >= 10)
                    wait();

                riderConfermati.put(rider);
                System.out.println("Il rider " + rider.getIdRider() + "di cognome " + rider.getCognome() + "è pronto");
                //Rilascia il monitor a tutti
                riderConfermati.notifyAll();
            }
        }

        public Rider consumaRider() throws InterruptedException {
            Rider riderDaOccupare = null;
            //Simile a ordine da consumare

            synchronized (riderConfermati){
                while(riderConfermati.isEmpty())
                    wait();

                riderDaOccupare = riderConfermati.take();
                System.out.println("Il rider " + riderDaOccupare.getIdRider() + "di cognome " + riderDaOccupare.getCognome() + "è stato rimosso");
                riderConfermati.wait();
            }
            return riderDaOccupare;
        }

        public ArrayList<Rider> getRiderConfermati(){return (ArrayList<Rider>) riderConfermati;}
    }

}
