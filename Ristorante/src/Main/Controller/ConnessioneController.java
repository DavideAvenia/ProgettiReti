package Controller;
// gestione delle connessioni con il server
// sulla porta 31000
import Model.Ordine;
import Model.Ristorante;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ConnessioneController {

    private int port = 31000;
    private Socket socket;
    private ArrayList<Ordine> OrdiniRicevuti = new ArrayList<Ordine>();

    //private BufferedReader in;
    //private PrintWriter out;
    private InetAddress addr = InetAddress.getByName("localhost");
    private static ConnessioneController instanza = null;
    private String idRistorante;

    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    private ConnessioneController(String id) throws IOException {

        socket = new Socket(this.addr, port);
        idRistorante = id;
        /*
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
     */

    }

    public static ConnessioneController getInstanza(String id) throws IOException {
        if(instanza == null){
            instanza = new ConnessioneController(id);
        }
        return instanza;
    }

    public void prendiOrdine() throws Exception {
        System.out.println("invio id del ristorante: " + idRistorante);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        // invio per il controllo
        Ristorante check = new Ristorante(idRistorante,null,null);
        oos.writeUnshared(check);

        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        Ordine o = (Ordine) ios.readUnshared();
        System.out.println("ordine ricevuto: " + o);
        OrdiniRicevuti.add(o);

    }

    public Socket getSocket() {
        return socket;
    }

}
