package Handlers;

import Model.Cliente;
import Model.Ordine;
import Model.Ristorante;
import Queries.ControllaIDCliente;
import Queries.ControllaIDRistorante;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


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

            if(ret!= null){
                ristoranteAttuale = ret;

                //Fai cose per gestire gli ordini
                //Credo che glieli debba inviare
            }else{
                //Manda l'oggetto Ristorante null
                oosRistorante.writeUnshared(null);
                System.out.println("Non ci sono clienti con questo ID");
            }


        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}


