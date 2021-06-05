package Controller;

import Model.Cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/*
Questa classe si occupa di gestire la connessione con il server
sulla porta memorizzata nella variabile 'port'. E' stata scelta la
porta 30000.
La socket creata per la comunicazione viene memorizzata nella variabile 'socket'.
Nella variabile 'cliente' viene memorizzato il cliente che ha effettuato l'accesso.
In questa classe è stato implementato il pattern singleton.
 */
public class ConnessioneController{
    private int port = 30000;
    private Socket socket;

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private BufferedReader in;
    private PrintWriter out;

    private InetAddress addr = InetAddress.getByName("localhost");
    private Cliente cliente;

    private static ConnessioneController connessioneController = null;

    /*
    Nel costruttore viene stabilita la connessione
     */
    private ConnessioneController() throws IOException {
        try{
            socket = new Socket(this.addr, port);
            System.out.println("Client Socket: " + socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);
        } catch(IOException e) {
            ois.close();
            oos.close();
            socket.close();
        }
    }

    public static ConnessioneController getInstanza() throws IOException {
        if(connessioneController == null){
            connessioneController = new ConnessioneController();
        }
        return connessioneController;
    }

    /*
    La funzione ha lo scopo di inviare l'id passato come attributo al server, per
    verificarne la presenza all'interno della base di dati.
    Viene aperto il canale di scrittura 'oos' dove viene inviaro il cliente, e viene
    ricevuta la risposta della sul canale di lettura 'ois' e salvata nella variabile 'ret'.
    Se non c'è corrispondenza allora il server risponde con un oggetto null e la funzione
    ritorna 'false', altrimenti la funzione ritorna 'true.
     */
    public boolean inviaIdCliente(String idCliente) throws IOException, ClassNotFoundException {
        Cliente invCliente = new Cliente(idCliente,null,null);

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeUnshared(invCliente);

        ois = new ObjectInputStream(socket.getInputStream());
        Cliente ret = (Cliente) ois.readUnshared();
        cliente = ret;
        if(ret == null){
            return false;
        }
        return true;
    }

    /*
    Funzione di accesso alla variabile 'cliente'
     */
    public Cliente getCliente() {
        return cliente;
    }

    /*
    Funzione di accesso alla variabile 'socket'.
     */
    public Socket getSocket() {
        return socket;
    }
}
