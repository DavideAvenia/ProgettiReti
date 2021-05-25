package Controller;
// gestione delle connessioni con il server
// sulla porta 31000
import Model.Ordine;
import Model.Rider;
import Model.Ristorante;
import Controller.ComunicazioneHandler.OrdineHandler;
import Controller.ComunicazioneHandler.ComunicazioneRiderHandler;
import Controller.ComunicazioneHandler.RiderConfermatiHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ConnessioneController extends Thread {

    private int port = 31000;
    private Socket socket;

    private InetAddress addr = InetAddress.getByName("localhost");
    private String idRistorante;


    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public ConnessioneController(String id) throws Exception {

        socket = new Socket(this.addr, port);
        idRistorante = id;
        run();

    }

    public void run()  {
        try{

            System.out.println("invio id del ristorante: " + idRistorante);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            // invio per il controllo
            Ristorante check = new Ristorante(idRistorante,null,null);
            oos.writeUnshared(check);

            ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
            Ordine o = (Ordine) ios.readUnshared();
            System.out.println("ordine ricevuto: " + o);
            OrdineHandler ordiniRicevuti = new ComunicazioneHandler.OrdineHandler();
            ordiniRicevuti.produceOrdine(o);

            //invio del primo rider che ha confermato al server
            // il primo rider della lista dovrebbe essere quello
            // che ha accettato il primo ordine del ristorante
            RiderConfermatiHandler riderDaInviare = new RiderConfermatiHandler();
            riderDaInviare.consumaRider();
            oos.writeObject(riderDaInviare);

        }catch (Exception e){
            System.err.println(e);
        }

    }

}
