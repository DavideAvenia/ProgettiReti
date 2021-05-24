package Handlers;

import Handlers.ComunicazioneHandler.OrdineHandler;
import Handlers.ComunicazioneHandler.VisualizzaRistorantiAttiviHandler;

import Model.Ordine;
import Model.Ristorante;

import Queries.ControllaIDRistorante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class RistoHandler extends Thread{
    private int port = 31000;
    private Socket socket;
    private ServerSocket ss = null;

    private Ristorante ristoranteAttuale;

    //La lista deve essere sincronizata per evitare che si fotta con altri client
    private List<Ordine> ordiniDaEseguire = Collections.synchronizedList(new ArrayList<>());

    public RistoHandler (Socket s) {
        socket = s;
        start();
    }

    public void run(){
        try {
            //Leggere l'id del ristorante e salvarlo
            ObjectInputStream iosRistorante = new ObjectInputStream(socket.getInputStream());
            Ristorante r = (Ristorante) iosRistorante.readUnshared();

            ControllaIDRistorante check = new ControllaIDRistorante();
            Ristorante ret = check.controllaIDQuery(r.getIdRistorante());

            ObjectOutputStream oosRistorante = new ObjectOutputStream(socket.getOutputStream());

            if(ret != null){
                ristoranteAttuale = ret;
                OrdineHandler ordineHandler = new OrdineHandler();

                VisualizzaRistorantiAttiviHandler ristorantiAttiviHandler = new VisualizzaRistorantiAttiviHandler();
                ristorantiAttiviHandler.produceRistorante(ret);

                //Thread-Safe se chiamato in locale
                //Non nel pattern consume
                Ordine ordine = ordineHandler.consumaOrdine(ristoranteAttuale);
                ordiniDaEseguire.add(ordine);

                //QUA DEVE INVIARE L'ORDINE
                //IL CONSUME SI OCCUPA SOLO DI CONSUMARE L'ORDINE
                //NON DI MANDARLO PURE

                //Manda l'ordine all'handler del ristorante
                ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                oosOrdine.writeUnshared(ordine);

                //Qua deve ricevere il rider
                //ObjectInputStream iosRider = new ObjectInputStream(socket.getInputStream());
                //iosRider.readUnshared();

                //Appena riceve conferma rider
                //ConfermaRiderHandler è la classe

                //In qualche modo deve mandarlo al cliente
                //Quando ha completato l'ordine
                //Chiama ordineHandler.consumaRistorante per rimuoverlo dai ristoranti online
            }else{
                //Manda l'oggetto Ristorante null
                oosRistorante.writeUnshared(null);
                System.out.println("Non ci sono ristoranti con questo ID");
                socket.close();
            }

        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


