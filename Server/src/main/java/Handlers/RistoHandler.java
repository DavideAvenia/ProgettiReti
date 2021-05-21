package Handlers;

import Main.Main.OrdineHandler;
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

            if(ret!= null){
                ristoranteAttuale = ret;

                //Thread-Safe se chiamato in locale
                //Non nel pattern consume
                OrdineHandler ordineHandler = new OrdineHandler();
                Ordine ordine = ordineHandler.consume(ristoranteAttuale);
                ordiniDaEseguire.add(ordine);

                //QUA DEVE INVIARE L'ORDINE
                //IL CONSUME SI OCCUPA SOLO DI CONSUMARE L'ORDINE
                //NON DI MANDARLO PURE

            }else{
                //Manda l'oggetto Ristorante null
                oosRistorante.writeUnshared(null);
                System.out.println("Non ci sono clienti con questo ID");
                socket.close();
            }

        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


