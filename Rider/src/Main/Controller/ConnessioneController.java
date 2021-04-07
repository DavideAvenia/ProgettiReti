package Controller;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;

    private static ConnessioneController instanza = null;

    private ConnessioneController() throws IOException {
        socket = new Socket(ip, port);

    }

    public static ConnessioneController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    public void conferma() throws IOException{

        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);


    }
}
