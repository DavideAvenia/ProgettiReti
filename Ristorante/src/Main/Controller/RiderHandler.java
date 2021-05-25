package Controller;
// connessione con i rider

import Controller.ComunicazioneHandler.OrdineHandler;
import Controller.ComunicazioneHandler.ComunicazioneRiderHandler;
import Controller.ComunicazioneHandler.RiderConfermatiHandler;


import Model.Ordine;
import Model.Rider;
import javafx.event.ActionEvent;
import javafx.event.Event;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    oos.writeObject("ok");
                    ComunicazioneRiderHandler riderConnessi = new ComunicazioneRiderHandler();
                    riderConnessi.produceRider(ret);

                    // invio dell'ordine al rider
                    // consumo l'ordine e il rider dall'handler
                    OrdineHandler ordine = new OrdineHandler();
                    Ordine o = ordine.consumaOrdine();

                    ComunicazioneRiderHandler riderCom = new ComunicazioneRiderHandler();
                    Rider riderLista = new Rider();
                    String rispostaRider = "annulla";
                    riderLista = riderCom.consumaRider();

                    // se non è quello con cui ho aperto la socket allora
                    // lo rimetto dentro
                    // solo se il rider che prendo dalla lista e il rider con il quale
                    // sto comunicando mando l'ordine da accettare
                   while(!rispostaRider.equals("annulla")){
                       while(riderLista != rider){
                           riderCom.produceRider(riderLista);
                           riderLista = riderCom.consumaRider();
                       }
                       // mando l'ordine al rider
                       oos.writeObject(o);
                       // ricevo la risposta dal rider
                       // solo se conferma esco
                       rispostaRider = (String) ios.readObject();
                   }

                    // metto il rider nella lista dei rider che hanno confermato
                    RiderConfermatiHandler riderconfermati = new RiderConfermatiHandler();
                    riderconfermati.produceRider(riderLista);
                }

                socket.close();
                System.out.println("socket chiusa");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("failed");
                e.printStackTrace();
            }
        }

};



