import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnessioneServer {
    private int port = 30000;
    private Socket socket;
    private ServerSocket ss = null;

    private static ConnessioneServer instanza = null;

    private ConnessioneServer() throws IOException {
        ss = new ServerSocket(port);
    }

    public static ConnessioneServer getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneServer();
        }
        return instanza;
    }

    public boolean accettaConnessioni() throws IOException {
        while (true) {
            Socket s = ss.accept();
            System.out.println("Connessione al server avvenuta");
        }
    }
}
