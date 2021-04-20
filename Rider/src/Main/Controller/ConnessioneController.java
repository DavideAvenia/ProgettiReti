package Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private InetAddress addr = InetAddress.getByName("localhost");

    private static ConnessioneController instanza = null;

    public ConnessioneController() throws IOException {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            out = new PrintWriter(new BufferedWriter(osw), true);

            socket = new Socket(this.addr, 30000);
            System.out.println("Client Socket: "+ socket); //Qui dovrebbe stabilire la connessione
        } catch(IOException e) {}
        try {
            //Sta roba deve scomparire forse


            start();
        } catch(IOException e1) {
            // in seguito ad ogni fallimento la socket deve essere chiusa, altrimenti
            // verrà chiusa dal metodo run() del thread
            try{
                socket.close();
            }catch(IOException e2){}
        }
    }

    public void start() {
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


    /*
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
    } */

}
