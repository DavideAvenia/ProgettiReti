package Controller;

import Model.Cliente;
import Model.Ordine;
import View.ConfermaOrdineView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/*
Questa classe si occupa di gestire gli ordini che arrivano dai
ristoranti e le funzionalità messe a disposizione della View
'ConfermaOrdineView' per confermare o annullare un ordine.
Nella variabile 'socket' viene memorizzata la socket di connessione
con il ristorante.
Anche in questa classe viene implementato il pattern singleton
impostando il costruttore privato e richiamandolo esclusivamente nella
funzione 'getInstanza'.
 */
public class ConfermaOrdineController {

    private Socket socket = ConnessioneController.getInstanza().getSocket();
    private static ConfermaOrdineController instanza = null;
    private Cliente c;

    private ConfermaOrdineController() throws IOException {
    }

    public static ConfermaOrdineController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConfermaOrdineController();
        }
        return instanza;
    }

    public void mostra() throws Exception {
        ConfermaOrdineView confermaordine = new ConfermaOrdineView();
        confermaordine.start(new Stage());
        System.out.println("finestra di conferma ordine mostrata, premi accetta o conferma");
    }

    /*
    Viene aperto il canale di lettura 'ois' su cui viene ricevuto l'ordine.
     */
    public String conferma() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Ordine o = (Ordine) ois.readUnshared();
        return o.getCliente().getIdCliente();
    }

    /*
    Il rider ha rifiutato l'ordine, quindi viene chiusa la socket.
     */
    public void annulla() throws Exception {
        System.out.println("ordine annullato");
        socket.close();
        System.out.println("chiusura socket");
    }

}



