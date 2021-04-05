import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnessioneServer {
    private int port = 30000;
    private Socket socket;

    private static ConnessioneServer instanza = null;

    private ConnessioneServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket s = serverSocket.accept();

        System.out.println("Connessione al server avvenuta");
    }

    public static ConnessioneServer getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneServer();
        }
        return instanza;
    }
}
