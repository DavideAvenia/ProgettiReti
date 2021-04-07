package Controller;

//rimozione rider dalla lista quando si scollegano


import java.io.*;
import java.net.*;
import java.util.*;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private ServerSocket serversocket;
    private Socket client;

    private ArrayList<String> idRider;
    private static ConnessioneController instanza = null;

    private ConnessioneController() throws IOException {
        serversocket = new ServerSocket(port);

    }

    public static ConnessioneController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    public boolean accettaConnessioni(String id) {

        try {
            Socket s = serversocket.accept();

            idRider.add(id);

            System.out.println("Connessione al server avvenuta");
            return true;
        }catch (IOException err){
            System.err.println("errore");
            return false;
        }
    }

    public ArrayList<String> getRider(){
        return idRider;
    }

    public void inviaRichiesta() throws IOException{
        for ( String iter:idRider) {

                client = new Socket(iter, port);
                OutputStream os = client.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);


                bw.write("Richiesta rider");
                bw.flush();
        }

    }


}
