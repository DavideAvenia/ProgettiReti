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
funzione 'getInstanza' che se non lo è già istanzia la variabile 'instanza',
e poi la ritorna.
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

    /*
    La funzione serve a ricevere l'ordine dal ristorante. Come prima cosa
    viene aperto il canale stream di lettura e poi viene letto l'ordine che
    viene salvato nella variabile 'ordine'.
    La funzione ritorna l'ordine che è stato ricevuto.
     */
    public Ordine checkRichieste() throws IOException, ClassNotFoundException {
        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        Ordine ordine = (Ordine) ios.readObject();
        return ordine;
    }

    public void mostra() throws Exception {
        ConfermaOrdineView confermaordine = new ConfermaOrdineView();
        confermaordine.start(new Stage());
    }

    /*
    La funzione ha il compito di inviare il messaggio di avvenuta conferma al ristorante.
    Come prima cosa viene aperto il canale stream di scrittura su cui viene scritto
    il messaggio "conferma". Poi viene preso il cliente dalla variabile 'c' e
    memorizzata nella variabile 'clienteDaServire' infine viene chiusa la socket.
    La funzione ritorna il cliente da servire.
     */
    public String conferma() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("conferma");
        System.out.println("ordine confermato");
        String clienteDaServire = c.getIdCliente() + c.getNome() + c.getCognome();
        socket.close();
        return clienteDaServire;
    }

    /*
    La funzione ha il compito di inviare il messaggio di annulla.
    Dopo aver aperto il canale stream di scrittura viene inviato il messaggio
    "annulla". Infine viene chiusa la socket.
     */
    public void annulla() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("annulla");
        System.out.println("ordine annullato");
        socket.close();
    }

}



