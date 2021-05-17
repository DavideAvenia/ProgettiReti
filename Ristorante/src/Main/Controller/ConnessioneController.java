package Controller;
// gestione delle connessioni con i rider
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnessioneController {

    private int port = 31000;
    private Socket socket;

    private static int counter = 0;
    private int id = ++counter;
    private BufferedReader in;
    private PrintWriter out;
    private InetAddress addr = InetAddress.getByName("localhost");
    private static ConnessioneController instanza = null;

    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public ConnessioneController() throws IOException {

        socket = new Socket(this.addr, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);


    }

    public static ConnessioneController getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    public Socket getSocket() {
        return socket;
    }

}
