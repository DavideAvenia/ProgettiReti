package Controller;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConnessioneController {
    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private ServerSocket serversocket;
    Socket client;

    private Map<Integer, Socket> clients = new HashMap<Integer, Socket>();
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

    public boolean accettaConnessioni() throws IOException {

            Socket s = serversocket.accept();
            clients.put(socket.getPort(), socket);

            System.out.println("Connessione al server avvenuta");

    }

    public void inviaRichiesta() throws IOException{
        for (Iterator<Integer> iter = clients.keySet().iterator(); iter.hasNext(); ) {
            int key = iter.next();

            client = clients.get(key);

                OutputStream os = client.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);


                bw.write("Richiesta rider");
                bw.flush();
        }

    }

    public void conferma() throws IOException{
        InputStreamReader isr = new InputStreamReader(client.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

    }

    public void v(){
        for (Iterator<Integer> iter = clients.keySet().iterator(); iter.hasNext(); ) {
            int key = iter.next();
            System.out.println(clients.get(key));
        }

    }

}
