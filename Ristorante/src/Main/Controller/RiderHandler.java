package Controller;

import Model.Ordine;
import Model.Rider;
import PatternPC.GestioneOrdini;
import PatternPC.GestioneRider;
import PatternPC.RiderDisponibili;
import Queries.ControllaID;

import java.io.*;
import java.net.Socket;

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
    se il ride non ha confermato, ...
     */
    public void run () {
        try {
            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
            rider = (Rider) ios.readObject();
            ControllaID check = new ControllaID();
            Model.Rider ret = check.controllaIDQuery(rider.getIdRider());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            if (ret == null) {
                //Manda l'oggetto rider null
                oos.writeObject(null);
                System.out.println("Non ci sono rider con questo ID");
            } else {
                //Manda l'oggetto rider creato
                System.out.println("rider connesso[" + ret.getIdRider() + "]: " + ret.getNome() + " " + ret.getCognome());
                oos.writeObject(ret);

                System.out.println("Sto producendo un rider");
                RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
                riderDisponibili.produceRider(ret);

                System.out.println("Sto andando a prendere un ordine da dare al rider");
                ObjectInputStream oisConferma = new ObjectInputStream(socket.getInputStream());
                String letturaConferma = (String) oisConferma.readUnshared();

                if(letturaConferma.equals("conferma")){
                    //Gestisco l'ordine solo dopo la conferma
                    GestioneOrdini gestioneOrdini = GestioneOrdini.getIstanza();
                    Ordine o = gestioneOrdini.consumaOrdine();

                    ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                    oosOrdine.writeUnshared(o);

                    Rider r;
                    ObjectInputStream oosRiderConfermato = new ObjectInputStream(socket.getInputStream());
                    r = (Rider) oosRiderConfermato.readUnshared();

                    GestioneRider gestioneRiderConfermati = GestioneRider.getIstanza();
                    gestioneRiderConfermati.produceRiderInviati(r);

                    System.out.println("Rider inviato e mi preparo a rimuoverlo");
                    sleep(10000);
                    gestioneRiderConfermati.consumaRiderInviati(r);
                }else{
                    //Altrimenti rimuovi il rider dalla lista dei rider
                }

            }

            socket.close();
            System.out.println("socket chiusa");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

};



