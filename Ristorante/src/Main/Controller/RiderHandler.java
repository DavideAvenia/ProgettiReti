package Controller;

import Model.Ordine;
import Model.Rider;
import PatternPC.GestioneOrdini;
import PatternPC.RiderDisponibili;
import Queries.ControllaID;

import java.io.*;
import java.net.Socket;

// controller per la connessione del ristorante con il rider
public class RiderHandler extends Thread {
    private int port = 32000;
    private Socket socket;

    private String idRistorante;
    private static Rider rider;
    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public RiderHandler(Socket s) throws Exception {
        socket = s;
        run();
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

                RiderDisponibili riderDisponibili = RiderDisponibili.getIstanza();
                riderDisponibili.produceRider(ret);

                // invio dell'ordine al rider
                // consumo l'ordine e il rider dall'handler
                GestioneOrdini gestioneOrdini = GestioneOrdini.getIstanza();
                Ordine o = gestioneOrdini.consumaOrdine();

                String rispostaRider = null;

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



