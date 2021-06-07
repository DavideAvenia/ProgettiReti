package Controller;

import Model.Ordine;
import Model.Rider;
import PatternPC.GestioneOrdini;
import PatternPC.GestioneRider;
import PatternPC.RiderDisponibili;
import Queries.ControllaID;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

/*
Questa classe si occupa di gestire la connessione tra il ristorante e il rider.
Viene creato un thread per ogni rider che si collega, in modo da avere
concorrenza tra i rider connessi.
 */
public class RiderHandler extends Thread {
    private int port = 32000;
    private Socket socket;

    private String idRistorante;
    private Rider rider;

    /*
    Il costruttore ha come parametri una variabile di tipo Socket che viene salvata
    nella variabile 'socket'. Infine fa partire la funzione run().
     */
    public RiderHandler(Socket s) throws Exception {
        socket = s;
        start();
    }

    /*
    Come prima cosa viene creato il canale stream di lettura con il rider,
    viene letto l'id inviato dal Rider e viene controllato nella base di dati.
    Se il controllo non va a buon fine viene scritto sul canale di scrittura un oggetto 'null',
    altrimenti manda l'oggetto rider che è stato trovato nella base di dati.
    Dato che il rider è stato confermato, viene aggiunto alla lista di rider disponibili
    a prendere un ordine, viene utilizzata la funzione 'produce' del pattern
    produttore-consumatore.
    A questo punto viene letto il messaggio del primo rider che ha risposto,
    se ha confermato allora si procede alla gestione dell'ordine: viene consumato dalla
    lista attraverso la funzione 'consume' del pattern produttore-consumatore, e viene
    inviato al rider attraverso il canale di scrittura. Infine il rider che ha confermato
    viene inserito nella lista di rider che hanno confermato e dopo una pausa viene
    eliminato.
    se il rider non ha confermato, viene interrotta la connessione con la chiusura della
    socket e interrotto il thread. Il Rider dovrà effettuare nuovamente l'accesso per
    ottenere un nuovo ordine.
     */
    public void run () {
        try {
            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
            System.out.println("leggo il rider");
            rider = (Rider) ios.readObject();
            System.out.println("faccio la query con l'id del rider ricevuto");
            ControllaID check = new ControllaID();
            Model.Rider ret = check.controllaIDQuery(rider.getIdRider());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            if (ret == null) {
                oos.writeObject(null);
                System.out.println("Non ci sono rider con questo ID");
            } else {
                System.out.println("rider connesso[" + ret.getIdRider() + "]: " + ret.getNome() + " " + ret.getCognome());
                System.out.println("Invio del rider ottenuto dalla base di dati");
                oos.writeObject(ret);

                System.out.println(">>>Sto producendo il rider disponibile");
                RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
                riderDisponibili.produceRider(ret);

                GestioneOrdini gestioneOrdini = GestioneOrdini.getIstanza();
                System.out.println(">>>consumo un ordine");
                Ordine ordineConsumato = gestioneOrdini.consumaOrdine();

                System.out.println(ordineConsumato.getCliente().getIdCliente());
                System.out.println(ordineConsumato.getProdotti());
                System.out.println(ordineConsumato.getRistorante().getIdRistorante());

                ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                System.out.println(">>>scrivo l'ordine al rider");
                oosOrdine.writeUnshared(ordineConsumato);

                /*ObjectInputStream oosRiderConfermato = new ObjectInputStream(socket.getInputStream());
                System.out.println("ricevo il rider confermato");
                Rider r = (Rider) oosRiderConfermato.readUnshared();

                GestioneRider gestioneRiderConfermati = GestioneRider.getIstanza();
                System.out.println("Produzione rider inviati");
                gestioneRiderConfermati.produceRiderInviati(r);*/

                System.out.println("Rider inviato e mi preparo a rimuoverlo");
                sleep(10000);

                socket.close();
            }

            } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

    }

};



