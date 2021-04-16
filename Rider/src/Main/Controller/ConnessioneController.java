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

    public boolean checkRichiesta() throws IOException{
        if(socket.isConnected()){
            System.out.println("controllo richieste");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            int stringa = br.read();
            if(stringa==1){
                return true;
            } else return false;
        } else {
            System.out.println("non connesso");
            return false;
        }

    }

    public Socket getSocket(){
        return socket;
    }

}
