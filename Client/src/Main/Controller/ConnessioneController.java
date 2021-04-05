package Controller;

import java.io.IOException;
import java.net.Socket;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;

    private static ConnessioneController instanza = null;

    private ConnessioneController() throws IOException {
        Socket socket = new Socket(ip, port);
    }

    public static ConnessioneController getInstanza() throws IOException {
        if(instanza == null){
            instanza = new ConnessioneController();
        }
        return instanza;
    }
}
