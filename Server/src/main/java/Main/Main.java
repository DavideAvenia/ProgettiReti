package Main;

import Handlers.ServerHandler;
import Model.Ordine;
import Model.Ristorante;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println(">Aperta la porta 30000 \n");
        ServerHandler serverHandlerCliente = new ServerHandler(30000);

        System.out.println(">Aperta la porta 31000 \n");
        ServerHandler serverHandlerRistorante = new ServerHandler(31000);

        System.out.println(">Server: inizializzato con porta 30000 \n");
        serverHandlerCliente.startServer();

        System.out.println(">Server: inizializzato con porta 31000 \n");
        serverHandlerRistorante.startServer();
    }

    public static class OrdineHandler{
        Map<Ristorante, Ordine> ordiniDaEseguire = new HashMap<>(10);


        public void produce(Ristorante ristorante, Ordine ordine) throws InterruptedException {
                synchronized (ordiniDaEseguire){
                    if(ordiniDaEseguire.size() == 10)
                        wait();

                    ordiniDaEseguire.put(ristorante, ordine);
                    System.out.println("Aggiunto il valore: al "+ ristorante.getNome() + " di " + ordine.getCliente().getCognome());
                    notify();
                    Thread.sleep(1000);
                }
        }

        public void consume(Ristorante ristorante, Ordine ordine) throws InterruptedException {
            while(true){
                synchronized (ordiniDaEseguire){
                    while(ordiniDaEseguire.isEmpty())
                        wait();

                    //Controllare se il ristorante è presente nella mappa
                    //Se c'è, consuma, altrimenti wait()
                    Iterator it = ordiniDaEseguire.entrySet().iterator();
                    while(it.hasNext()){
                        Map.Entry pair = (Map.Entry) it.next();
                        if(pair.getKey().equals(ristorante)){
                            System.out.println("Elaborato l'ordine: al "+ pair.getKey() + " di " + pair.getValue());

                            //Questi due oggetti li devo passare al ristoHandler che poi deve inviare al rider
                            //Ristorante ristorante = pair.getKey();
                            //Ordine ordine = pair.getValue();

                            ordiniDaEseguire.remove(ristorante, ordine);


                            notify();
                        }
                    }

                    Thread.sleep(1000);
                }
            }
        }
    }
}

