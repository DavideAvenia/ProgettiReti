package Handlers;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ComunicazioneHandler {
    //Questi due Handler servono per gestire la chiamate asincrone che avverranno tra i ristoranti e clienti
    public static class OrdineHandler{
        //Questa classe si occupa di gestire gli ordini
        //E' statica perché ci deve essere un unica instanza di essa
        //Deve essere chiamata localmente quando deve produrre o deve consumare PER GLI ORDINI

        //Questa lista si trova in una parte della memoria dov'è condivisa con tutti i thread che chiamano questi metodi
        List<Ordine> ordiniDaEseguire = Collections.synchronizedList(new ArrayList<Ordine>());

        public void produceOrdine(Ordine ordine) throws InterruptedException {
            synchronized (ordiniDaEseguire) {
                //Il produttore non può produrre quando ci sono 10 o più ordini
                while(ordiniDaEseguire.size() >= 10)
                    ordiniDaEseguire.wait();

                //Inserisce l'ordine nella coda
                System.out.println(ordiniDaEseguire.add(ordine));
                System.out.println("Aggiunto il valore: al " + ordine.getRistorante().getNome() + " di " + ordine.getCliente().getCognome());
                //Rilascia il monitor a tutti
                System.out.println(ordiniDaEseguire.size());
                ordiniDaEseguire.notifyAll();
            }
        }

        public Ordine consumaOrdine(Ristorante ristorante) throws InterruptedException {
            Ordine ordineDaImportare = null;
            synchronized (ordiniDaEseguire){
                System.out.println(ordiniDaEseguire);
                //Il consumatore non può consumare quando la linkedList è vuota
                while(ordiniDaEseguire.isEmpty())
                    ordiniDaEseguire.wait();

                System.out.println("Sto controllando gli ordini da eseguire");
                for (Ordine o:ordiniDaEseguire) {
                    //Si usa l'equals e non ==
                    if(o.getRistorante().equals(ristorante)){
                        System.out.println("Elaborato l'ordine: al "+ o.getRistorante() + " di " + o.getCliente());

                        //Prende l'oggetto da da passare al ristorante
                        ordineDaImportare = o;

                        //Lo rimuove dalla lista
                        ordiniDaEseguire.remove(o);
                    }
                }
                ordiniDaEseguire.notify();
                return ordineDaImportare;
            }
        }
    }

    public static class VisualizzaRistorantiAttiviHandler{
        //Anche questa classe è condivisa
        //Si occupa del controllare se i ristoranti sono attivi
        List<Ristorante> ristorantiAttivi = Collections.synchronizedList(new ArrayList<>());

        public void produceRistorante(Ristorante ristorante) throws InterruptedException {
            //Deve essere chiamato da un ristorante solo quando va Online
            synchronized (ristorantiAttivi) {
                while(ristorantiAttivi.size() >= 10)
                    ristorantiAttivi.wait();

                System.out.println(ristorantiAttivi.add(ristorante));
                System.out.println("Il ristorante " + ristorante.getNome() + " con id " + ristorante.getIdRistorante() + ".");
                System.out.println(ristorantiAttivi.size());
                ristorantiAttivi.notify();
            }
        }

        public Ristorante consumaRistorante(Ristorante ristorante) throws InterruptedException {
            //Deve essere chiamato da un ristorante solo quando va Offline
            Ristorante ristoranteDaDisattivare = null;
            synchronized (ristorantiAttivi){
                System.out.println(ristorantiAttivi);
                while(ristorantiAttivi.isEmpty() && ristorantiAttivi.contains(ristorante))
                    ristorantiAttivi.wait();

                for (Ristorante r:ristorantiAttivi) {
                    if(r.equals(ristorante)){
                        System.out.println("Il ristorante " + ristorante.getNome() + " è andato offline.");
                        ristoranteDaDisattivare = ristorante;
                        ristorantiAttivi.remove(ristorante);
                    }
                }
                ristorantiAttivi.notifyAll();
                return ristoranteDaDisattivare;
            }
        }

        public boolean controllaPresenzaRistorante(Ristorante ristorante) throws InterruptedException {
            synchronized (ristorantiAttivi){
                System.out.println(ristorantiAttivi);
                while(ristorantiAttivi.isEmpty())
                    ristorantiAttivi.wait();
                while(!ristorantiAttivi.contains(ristorante))
                    ristorantiAttivi.wait();
                ristorantiAttivi.notify();
                return ristorantiAttivi.contains(ristorante);
            }
        }
    }

    public static class ConfermeRiderHandler{
        BlockingQueue<Rider> riderDisponibili = new LinkedBlockingQueue<>(10);

        public void produceRider(Rider rider) throws InterruptedException {
            synchronized (riderDisponibili) {
                //Il produttore non può produrre quando ci sono 10 o più rider
                while(riderDisponibili.size() >= 10)
                    riderDisponibili.wait();

                System.out.println(riderDisponibili.offer(rider));
                System.out.println("Il rider " + rider.getIdRider() + "di cognome " + rider.getCognome() + "è pronto");
                //Rilascia il monitor a tutti
                riderDisponibili.notify();
            }
        }

        public Rider consumaRider() throws InterruptedException {
            Rider riderDaOccupare = null;
            //Simile a ordine da consumare
            synchronized (riderDisponibili){
                System.out.println(riderDisponibili);
                while(riderDisponibili.isEmpty())
                    riderDisponibili.wait();

                //adesso prende il rider in testa, cioè il primo disponibile
                //Non credo debba fare sta cosa, nel caso lo cambiamo
                riderDaOccupare = riderDisponibili.take();
                System.out.println("Il rider " + riderDaOccupare.getIdRider() + "di cognome " + riderDaOccupare.getCognome() + "è stato rimosso");
                riderDisponibili.notify();
                return riderDaOccupare;
            }

        }
    }
}
