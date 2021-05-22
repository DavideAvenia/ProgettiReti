package Main;

import Handlers.RistoHandler;
import Handlers.ServerHandler;
import Model.Ordine;
import Model.Ristorante;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println(">Aperta la porta 30000");
        ServerHandler serverHandlerCliente = new ServerHandler(30000);

        System.out.println(">Aperta la porta 31000");
        ServerHandler serverHandlerRistorante = new ServerHandler(31000);

        System.out.println(">Server: inizializzato con porta 30000");
        serverHandlerCliente.startServer();

        System.out.println(">Server: inizializzato con porta 31000");
        serverHandlerRistorante.startServer();
    }

    public static class OrdineHandler{
        //Questa classe si occupa di gestire gli ordini
        //E' statica perché ci deve essere un unica instanza di essa
        //Deve essere chiamata localmente quando deve produrre o deve consumare PER GLI ORDINI

        //Questa lista si trova in una parte della memoria dov'è condivisa con tutti i thread che chiamano questi metodi
        BlockingQueue<Ordine> ordiniDaEseguire = new LinkedBlockingQueue();

        public void produceOrdine(Ordine ordine) throws InterruptedException {
            synchronized (ordiniDaEseguire) {
                //Il produttore non può produrre quando ci sono 10 o più ordini
                while(ordiniDaEseguire.size() >= 10)
                    wait();

                //Inserisce l'ordine nella coda
                ordiniDaEseguire.put(ordine);
                System.out.println("Aggiunto il valore: al " + ordine.getRistorante().getNome() + " di " + ordine.getCliente().getCognome());
                //Rilascia il monitor a tutti
                ordiniDaEseguire.notifyAll();
            }
        }

        public Ordine consumaOrdine(Ristorante ristorante) throws InterruptedException {
            Ordine ordineDaImportare = null;
            synchronized (ordiniDaEseguire){
                //Il consumatore non può consumare quando la linkedList è vuota
                while(ordiniDaEseguire.isEmpty())
                    wait();

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
                ordiniDaEseguire.notifyAll();
                return ordineDaImportare;
            }

        }

        //Anche questa è condivisa, ma dovrà essere chiamata solo dai ristoranti
        //Quindi condivisa solo tra i ristoHandler
        //Ma avrà un metodo che chiamerà SOLO il clientHandler per vedere se il ristorante è presente
        BlockingQueue<Ristorante> ristorantiAttivi = new LinkedBlockingQueue();

        public void produceRistorante(Ristorante ristorante) throws InterruptedException {
            synchronized (ristorantiAttivi) {
                while(ristorantiAttivi.size() >= 10)
                    wait();

                ristorantiAttivi.put(ristorante);
                System.out.println("Il ristorante " + ristorante.getNome() + " con id " + ristorante.getIdRistorante() + ".");
                notifyAll();
            }
        }

        public Ristorante consumaRistorante(Ristorante ristorante) throws InterruptedException {
            Ristorante ristoranteDaDisattivare = null;
            synchronized (ristorantiAttivi){
                while(ristorantiAttivi.isEmpty())
                    wait();

                for (Ristorante r:ristorantiAttivi) {
                    if(r.equals(ristorante)){
                        System.out.println("Il ristorante " + ristorante.getNome() + " è andato offline.");
                        ristoranteDaDisattivare = ristorante;
                        ristorantiAttivi.remove(ristorante);
                    }
                }
                ristorantiAttivi.notifyAll();
            }
            return ristoranteDaDisattivare;
        }
    }
}



