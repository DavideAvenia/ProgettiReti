package Controller;

import View.VisualizzaRiderView;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConnessioneController {
    private int port = 30000;
    private Socket s;
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

    public boolean accettaConnessioni() {

        try {
            s = serversocket.accept();
            System.out.println("connesso");
            //idRider.add(id);
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
        //for ( String r:idRider) {
        //rimozione rider dalla lista quando non sono più collegati

                if(s.isConnected()){
                    OutputStream os = s.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(1);
                    bw.flush();
                    System.out.println("richiesta inviata");

                } else {
                    client.close();
                    //idRider.remove(r);
                }

        //}

    }

    public void inviaIDOrdine() throws IOException{
        System.out.println("attendo risposta di conferma");
     //   String r = idRider.pollFirst();
     //   client = new Socket(r, port);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        int stringa = br.read();

        System.out.println(stringa);
        if(stringa==1){
            //riderOccupati.add(r);
           // VisualizzaRiderView visualizzaRider = new VisualizzaRiderView();
           // visualizzaRider.cambiaLabel("arrivato");
            System.out.println("ordine confermato");

        } else if (stringa==0){
            //idRider.add(r);
            System.out.println("ordine annullato");
        }
    }


}
