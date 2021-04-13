package Controller;

import View.VisualizzaRiderView;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private ServerSocket serversocket;
    private Socket client;

    private LinkedList<String> idRider = new LinkedList<String>();
    private List<String> riderOccupati = new LinkedList<>();

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

    public List<String> getRider(){
        return idRider;
    }

    public void inviaRichiesta() throws IOException{
        for ( String r:idRider) {
        //rimozione rider dalla lista quando non sono più collegati
                client = new Socket(r, port);
                if(client.isConnected()){
                    OutputStream os = client.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);

                    bw.write("Richiesta rider");
                    bw.flush();



                } else {
                    client.close();
                    idRider.remove(r);
                }

        }

    }

    public void inviaIDOrdine() throws IOException{

        String r = idRider.pollFirst();
        client = new Socket(r, port);
        InputStream is = client.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String stringa = new String();
        stringa = br.readLine();

        if(stringa.equals("conferma")){
            riderOccupati.add(r);
            VisualizzaRiderView visualizzaRider = new VisualizzaRiderView();
            visualizzaRider.cambiaLabel("arrivato");

        } else if (stringa.equals("annulla")){
            idRider.add(r);
        }
    }


}
