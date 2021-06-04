package Controller;

import Model.Rider;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/*
Questa classe si occupa di gestire la gestire la connessione
con il ristorante.
Nella variabile 'addr' viene memorizzato l'indirizzo a cui il rider
deve connettersi, mentre nella variabile 'port' la porta dedicata
alla connessione Ristorante-Rider, in questo caso è stata scelta la
porta 32000.
Le variabili 'ois' e 'oos' sono canali stream, rispettivamente
di lettura e di scrittura.
Nella variabile 'riderAttuale' viene memorizzato il rider che
accede tramite l'id.
In questa classe viene implementato il pattern singleton, il costruttore
infatti è privato e viene chiamato dalla funzione 'getInstanza'.
Se non è stata già assegnata, assegna alla variabile 'instanza' un nuovo
oggetto di tipo 'ConnessioneController', in ogni caso la funzione
ritorna la variabile 'instanza'.
 */
public class ConnessioneController {
    private String ip = "localhost";
    private int port = 32000;
    private Socket socket;
    private InetAddress addr = InetAddress.getByName("localhost");
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private static ConnessioneController instanza = null;

    private Rider riderAttuale;

    /*
    Il costruttore crea la socket di connessione con il ristorante.
     */
    private ConnessioneController() throws IOException {
        try{
            socket = new Socket(this.addr, port);
            System.out.println("Client Socket: "+ socket);
        } catch(IOException e) {
            socket.close();
            e.printStackTrace();
        }
    }

    /*
    Funzione di accesso alla variabile 'socket'.
     */
    public Socket getSocket(){
        return socket;
    }

    public static ConnessioneController getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    /*
    La funzione ha il compito di inviare l'id del Rider al ristorante, per
    controllarne la presenza nella base di dati.
    Viene una nuova variabile di tipo Rider con l'id specificato negli attributi
    della funzione. Dopo di che viene creato il canale stram di scrittura dove viene
    inviato il Rider appena creato. Viene creato anche il canale stream di lettura
    dove se viene letto un Rider allora viene assegnato alla variabile 'RiderAttuale' e
    la funzione ritorna 'true', altrimenti la funzione ritorna false, cioè non è
    stato trovato il rider nella base di dati.
     */
    public boolean inviaIdRider(String idRider) throws IOException, ClassNotFoundException {

        Rider invRider = new Rider(idRider, null, null);
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(invRider);

        ois = new ObjectInputStream(socket.getInputStream());
        Rider ret;
        ret = (Rider) ois.readObject();

        if (ret != null) {
            riderAttuale = ret;
            return true;
        } else
            return false;
    }

    /*
    La funzione ha il compito di chiudere la socket.
     */
    public void chiudiSocket() throws IOException {
        socket.close();
    }
}


