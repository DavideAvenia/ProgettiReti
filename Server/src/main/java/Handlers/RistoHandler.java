package Handlers;

import Model.Ordine;
import Model.Rider;
import Model.Ristorante;

import PatternPC.ConfermaRider;
import PatternPC.OrdiniDaEseguire;
import PatternPC.VisualizzaRistorantiAttivi;
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
                System.out.println(">>è stato richiesto il ristorante ["+ret.getIdRistorante()+"]: "+ret.getNome());
                oosRistorante.writeUnshared(ret);

                System.out.println(">>Inserisco nei ristoranti attivi il ristorante attuale");
                VisualizzaRistorantiAttivi visualizzaRistorantiAttivi = VisualizzaRistorantiAttivi.getIstanza();
                visualizzaRistorantiAttivi.produceRistorante(ristoranteAttuale);

                //Thread-Safe se chiamato in locale
                System.out.println(">>Sto controllando gli ordini da eseguire FUORI");

                OrdiniDaEseguire ordiniDaEseguire = OrdiniDaEseguire.getIstanza();
                Ordine ordine = ordiniDaEseguire.consumaOrdine(ristoranteAttuale);

                ordiniDaEseguire.visualizzaListaOrdiniDaEseguire();

                //Manda l'ordine all'handler del ristorante
                System.out.println(">>Sto scrivendo l'ordine da eseguire");
                ObjectOutputStream oosOrdine = new ObjectOutputStream(socket.getOutputStream());
                oosOrdine.writeUnshared(ordine);

                //Qua deve ricevere il rider
                ObjectInputStream iosRider = new ObjectInputStream(socket.getInputStream());
                Rider rider = (Rider) iosRider.readUnshared();

                //Qui produrrà il rider
                ConfermaRider confermaRider = ConfermaRider.getIstanza();
                confermaRider.produceRider(rider);

                System.out.println(">>Si controlla l'esistenza del rider all'interno di un ordine");
                ordiniDaEseguire.controllaPresenzaOrdineEseguito(rider);

                sleep(10000);
                System.out.println(">>Ordine eseguito con successo");

                //Quando ha completato l'ordine
                //Chiama consumaRistorante per rimuoverlo dai ristoranti online

                //Leva il commento quando finisco tutto
                visualizzaRistorantiAttivi.consumaRistorante(ret);

                socket.close();
                this.interrupt();
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


