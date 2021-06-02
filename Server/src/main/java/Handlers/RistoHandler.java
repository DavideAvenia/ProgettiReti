package Handlers;

import Handlers.ComunicazioneHandler.OrdineHandler;
import Handlers.ComunicazioneHandler.VisualizzaRistorantiAttiviHandler;
import Handlers.ComunicazioneHandler.ConfermeRiderHandler;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import PatternPC.OrdiniDaEseguire;
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
                System.out.println("è stato richiesto l'utente["+ret.getIdRistorante()+"]: "+ret.getNome());

                System.out.println("Instanza ordineHandler");
                VisualizzaRistorantiAttiviHandler ristorantiAttiviHandler = new VisualizzaRistorantiAttiviHandler();
                ristorantiAttiviHandler.produceRistorante(ret);

                //Thread-Safe se chiamato in locale
                System.out.println("Sto controllando gli ordini da eseguire NEL HANDLER");

                OrdiniDaEseguire ordiniDaEseguire = OrdiniDaEseguire.getIstanza();
                ordiniDaEseguire.consumaOrdine(ristoranteAttuale);

                ordiniDaEseguire.visualizzaLista();

                //Manda l'ordine all'handler del ristorante

                /*System.out.println("Sto scrivendo l'ordine da eseguire");
                ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                oosOrdine.writeUnshared(ordine);

                //Qua deve ricevere il rider
                ObjectInputStream iosRider = new ObjectInputStream(socket.getInputStream());
                Rider rider = (Rider) iosRider.readUnshared();

                //Qui produrrà il rider
                //ConfermaRiderHandler prende l'instanza
                ComunicazioneHandler.ConfermeRiderHandler confermaRider = new ComunicazioneHandler.ConfermeRiderHandler();
                confermaRider.produceRider(rider);

                //Appena riceve conferma rider
                //In qualche modo deve mandarlo al cliente
                //Tramite il ConfermaRiderHandler che il cliente deve chiamare il consumaRider

                //Quando ha completato l'ordine
                //Chiama ordineHandler.consumaRistorante per rimuoverlo dai ristoranti online
                
                //ristorantiAttiviHandler.consumaRistorante(ret);*/

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


