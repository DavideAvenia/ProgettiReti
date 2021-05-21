package Main;

import Handlers.RistoHandler;
import Handlers.ServerHandler;
import Model.Ordine;
import Model.Ristorante;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        BlockingQueue<Ordine> ordiniDaEseguire = new LinkedBlockingQueue();

        public void produce(Ordine ordine) throws InterruptedException {
            synchronized (ordiniDaEseguire) {
                while(ordiniDaEseguire.size() >= 10)
                    wait();

                ordiniDaEseguire.put(ordine);
                System.out.println("Aggiunto il valore: al " + ordine.getRistorante().getNome() + " di " + ordine.getCliente().getCognome());
                ordiniDaEseguire.notifyAll();
                Thread.sleep(1000);
            }
        }

        public void consume(Ristorante ristorante, Socket socket) throws Exception {
            synchronized (ordiniDaEseguire){
                while(ordiniDaEseguire.isEmpty())
                    wait();

                for (Ordine o:ordiniDaEseguire) {
                    if(o.getRistorante().equals(ristorante)){
                        System.out.println("Elaborato l'ordine: al "+ o.getRistorante() + " di " + o.getCliente());
                        //Qui deve prendere l'oggetto del ristoHandler e mandarlo al ristorante
                        //Che lo manderà al rider con già contenente l'ID del cliente
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeUnshared(o);
                        System.out.println("ordine inviato al ristorante");

                        ordiniDaEseguire.remove(o);
                        ordiniDaEseguire.notifyAll();
                    }
                }
            }
            Thread.sleep(1000);
        }
    }
}



