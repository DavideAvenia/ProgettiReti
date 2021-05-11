package Controller;

import Model.Rider;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
// controller per la connessione del ristorante con il rider

public class ConnessioneController extends Thread{
    private int port = 30000;
    private Socket socket;

    private static int counter = 0;
    private int id = ++counter;
    private BufferedReader in;
    private PrintWriter out;
    private String idRistorante;
    private ArrayList<Socket> riderConnessi = new ArrayList<>();

    private static ConnessioneController instanza = null;

    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public ConnessioneController(Socket s, String i) throws IOException {
        socket = s;
        idRistorante = i;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        System.out.println("ServerThread " + id + ": started");
        run();
    }

    // se la socket è connessa invia la richiesta al rider connesso su quella socket
    // e aspetta la risposta
    // se il rider accetta allora gli viene inviato il codice dell'ordine
   public void run() {
       try {

           ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());

           Rider r = (Rider) ios.readObject();
           ControllaID check = new ControllaID();
           Model.Rider ret = check.controllaIDQuery(r.getIdRider());

           // controllo che l'id del rider è presente del DB
           ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
           if(ret == null) {
               //Manda l'oggetto rider null
               oos.writeObject(null);
               System.out.println("Non ci sono rider con questo ID");
           }else{
               //Manda l'oggetto rider creato
               System.out.println("è stato richiesto l'utente["+ret.getIdRider()+"]: "+ret.getNome()+" "+ret.getCognome());
               oos.writeObject(ret);
               System.out.println("Inviato");
               //Mandagli i ristoranti attivi
           }

               //INVIO RICHIESTA AL RIDER
               if(socket.isConnected()){
                   riderConnessi.add(socket);
                   out.write(1);
                   out.flush();
                   System.out.println("richiesta inviata");
                   System.out.println("attendo risposta di conferma");
                   String idRider = in.readLine();
                   // se il rider conferma allora viene inviato l'id altrimenti
                   // invia una stringa "annulla"
                   if(!idRider.equals("annulla")) {
                       System.out.println("id del rider che ha risposto: " + idRider);
                       //Se il rider conferma l'ordine il ristorante invia
                       //l'id dell'ordine corrispondente
                       System.out.println("ordine confermato");
                       String idOrdine = "1234";
                       out.write(idOrdine + "\n");
                       out.flush();
                       System.out.println("id ordine e id ristorante inviato");

                       //controllo che l'ordine sia consegnato
                       String stato = in.readLine();
                       System.out.println("ordine: " + stato);

                   } else System.out.println("ordine annullato");
                   socket.close();
                   System.out.println("sessione terminata");
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
