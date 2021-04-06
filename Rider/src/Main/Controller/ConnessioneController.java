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

    private Map<Integer, Socket> clients = new HashMap<Integer, Socket>();
    private static ConnessioneController instanza = null;

    private ConnessioneController() throws IOException {
        Socket socket = new Socket(ip, port);

    }

    public static ConnessioneController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConnessioneController();
        }
        return instanza;
    }

    public void inviaRichiesta() throws IOException{
        for (Iterator<Integer> iter = clients.keySet().iterator(); iter.hasNext(); ) {
            int key = iter.next();

            Socket client = clients.get(key);

// Sending the response back to the client.
            try {
                OutputStream os = client.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);


                bw.write("Richiesta rider");
                bw.flush();
            } catch (IOException er) {
                System.err.println("errore");
            }

            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }


        }
    }
}
