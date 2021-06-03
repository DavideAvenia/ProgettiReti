package Controller;

import Model.Ordine;
import Model.Rider;
import PatternPC.GestioneOrdini;
import PatternPC.GestioneRider;
import PatternPC.RiderDisponibili;
import Queries.ControllaID;

import java.io.*;
import java.net.Socket;

// controller per la connessione del ristorante con il rider
public class RiderHandler extends Thread {
    private int port = 32000;
    private Socket socket;

    private String idRistorante;
    private Rider rider;
    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public RiderHandler(Socket s) throws Exception {
        socket = s;
        start();
    }

    public void run () {
        try {
            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
            // controllo che l'id del rider è presente del DB
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
                // viene inviato un ok al rider
                // per confermare che l'id è nel DB, quindi viene aggiunto
                // alla lista di rider connessi
                oos.writeObject(ret);

                System.out.println("Sto producendo un rider");
                RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
                riderDisponibili.produceRider(ret);

                // invio dell'ordine al rider
                // consumo l'ordine e il rider dall'handler
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


                // se non è quello con cui ho aperto la socket allora
                // lo rimetto dentro
                // solo se il rider che prendo dalla lista e il rider con il quale
                // sto comunicando mando l'ordine da accettare
            }

            socket.close();
            System.out.println("socket chiusa");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

};



