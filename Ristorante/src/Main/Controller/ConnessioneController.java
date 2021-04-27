package Controller;

import java.io.*;
import java.net.*;
// controller per la connessione del ristorante con il rider

public class ConnessioneController extends Thread{
    private int port = 30000;
    private Socket socket;

    private static int counter = 0;
    private int id = ++counter;
    private BufferedReader in;
    private PrintWriter out;
    private String idRider;

    private static ConnessioneController instanza = null;

    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public ConnessioneController(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        System.out.println("ServerThread " + id + ": started");
        run();
    }

   /* private ConnessioneController() throws IOException {
        serversocket = new ServerSocket(port);

    }

    public static ConnessioneController getInstanza() throws IOException {
        if (instanza == null) {
            instanza = new ConnessioneController();
        }
        return instanza;
    }
*/


    // se la socket è connessa invia la richiesta al rider connesso su quella socket
    // e aspetta la risposta
    // se il rider accetta allora gli viene inviato il codice dell'ordine
   public void run() {
       try {

               //String str = in.readLine();
               //if (str.equals("END")) break;
               System.out.println("ServerThread "+id+": echoing -> ");
               //out.println(str);

               //INVIO RICHIESTA AL RIDER
               if(socket.isConnected()){

                   out.write(1);
                   out.flush();
                   System.out.println("richiesta inviata");
                   System.out.println("attendo risposta di conferma");
                   idRider = in.readLine();
                   System.out.println("id del rider che ha risposto: " + idRider);
                   // se il rider conferma allora viene inviato l'id altrimenti
                   // invia una stringa "annulla"
                   if(!idRider.equals("annulla")) {

                       //Se il rider conferma l'ordine il ristorante invia
                       //l'id dell'ordine corrispondente
                       System.out.println("ordine confermato");
                       String idOrdine = "1234";
                       out.write(idOrdine + "\n");
                       out.flush();
                       System.out.println("id ordine inviato");

                   }
               }else {
                   System.out.println("connection problem");
               }
          // System.out.println("ServerThread "+id+": closing...");
       } catch (Exception e) {}

       try {
           socket.close();
       } catch(IOException e) {}
   }

}
