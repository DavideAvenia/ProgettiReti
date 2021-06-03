package Controller;
// gestione delle connessioni con il server
// sulla porta 31000

import Model.Ristorante;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController{

    private int port = 31000;
    private Socket socket;
    private InetAddress addr = InetAddress.getByName("localhost");

    private static ConnessioneController instanza = null;
    private Ristorante ristoranteAttuale;

    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    private ConnessioneController() throws Exception {
        socket = new Socket(this.addr, port);
    }

    public static ConnessioneController getInstanza() throws Exception {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    public boolean controllaIdRistorante(String id) throws IOException, ClassNotFoundException {
        //Qui deve inviare l'id al server tramite la socket
        Ristorante invRistorante = new Ristorante(id, null, null);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(invRistorante);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Ristorante ret = (Ristorante) ois.readUnshared();
        ristoranteAttuale = ret;
        if(ret == null){
            return false;
        }
        return true;
    }

    public Socket getSocket() {
        return socket;
    }
}
