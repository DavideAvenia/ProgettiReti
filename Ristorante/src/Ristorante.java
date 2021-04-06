
import java.net.*;
import java.nio.Buffer;
import java.util.*;
import java.io.*;

public class Ristorante {
    private Map<Integer, Socket> clients = new HashMap<Integer, Socket>();
    private int port;
    private ServerSocket sersocket;
    private Socket socket;

    public void connessioneRider() {
        try {
            sersocket = new ServerSocket(port);
            socket = sersocket.accept();
            clients.put(socket.getPort(), socket);
        } catch (IOException ex) {
            System.err.println("errore");
        }
    }

    public void visualizzaRider() {
        System.out.println("Rider connessi: ");

        for (Iterator<Integer> iter = clients.keySet().iterator(); iter.hasNext(); ) {
            int key = iter.next();
            System.out.println(clients.get(key));
        }
    }

    public void inviaRichiesta() {
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

                InputStreamReader isr = new InputStreamReader(client.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String line;
                while( (line=br.readLine())!=null )
                {
                    System.out.println(line);
                }
            } catch (IOException er) {
                System.err.println("errore");
            }


        }

    }
}


