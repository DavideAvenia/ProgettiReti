package Controller;

import Model.Ristorante;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/*
Questa classe si occupa di gestire la connessione tra il ristorante e il server.
E' stato scelto di gestire la comunicazione sulla porta 31000, salvata nella
variabile 'port'.
Nella variabile 'socket' verrà salvata la socket di una specifica comunicazione tra
il server ed uno specifico ristorante, che viene salvato nella variabile 'ristorante attuale'.
La variabile 'addr' memorizza l'indirizzo a cui il ristorante deve collegarsi.
 */
public class ConnessioneController{

    private int port = 31000;
    private Socket socket;
    //private InetAddress addr = InetAddress.getByName("10.113.103.251");
    private InetAddress addr = InetAddress.getByName("localhost");

    private static ConnessioneController instanza = null;
    private Ristorante ristoranteAttuale;

    /*
    Nel costruttore viene generata una nuova socket e salvata nella variabile 'socket'.
    Per implementare il pattern singleton è necessario che il costruttore sia privato,
    è la funzione 'getInstanza' che svolge la funzione di creare una sola istanza
    della classe, e, qualora esista già, semplicemnte di di ritornarla.
     */
    private ConnessioneController() throws Exception {
        socket = new Socket(this.addr, port);
    }

    public static ConnessioneController getInstanza() throws Exception {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    /*
    La funzione ha il compito di inviare l'id del ristorante al server, che si occuperà
    di controllare che l'id esista nella basi di dati.
    Come prima cosa viene aperto il canale di stream per la scrittura 'oos' e viene
    inviato il ristorante al server. Poi viene aperto il canale di stream per la
    lettura 'ios' in modo da poter ricevere la risposta dal server. Se il controllo
    non è andato a buon fine la funzione ritornerà 'false' altrimenti ritornerà 'true'.
     */
    public boolean controllaIdRistorante(String id) throws IOException, ClassNotFoundException {
        Ristorante invRistorante = new Ristorante(id, null, null);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Invio del ristorante");
        oos.writeUnshared(invRistorante);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Ricevo il ristorante di conferma");
        Ristorante ret = (Ristorante) ois.readUnshared();
        ristoranteAttuale = ret;
        if(ret == null){
            return false;
        }
        return true;
    }

    /*
    Funzione di accesso alla variabile 'socket'.
     */
    public Socket getSocket() {
        return socket;
    }
}
