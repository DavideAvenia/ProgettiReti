package Controller;
// connessione con i rider

import Model.Ordine;
import Model.Rider;
import javafx.event.ActionEvent;
import javafx.event.Event;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
// controller per la connessione del ristorante con il rider

public class RiderHandler extends Thread {
    private int port = 32000;
    private Socket socket;

    private static int counter = 0;
    private int id = ++counter;
    private BufferedReader in;
    private PrintWriter out;
    private String idRistorante;
    private static ArrayList<String> riderConnessi = new ArrayList<>();
    //private static boolean connesso = false;
    private static RiderHandler instanza = null;

    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public RiderHandler(Socket s) throws Exception {
        socket = s;
        run();
    }

    public Socket getSocket() {
        return socket;
    }

    public static void inviaOrdine(ArrayList<Ordine> ordini) throws IOException, ClassNotFoundException {
        Socket socket = instanza.socket;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(ordini.get(0));
        ordini.remove(0);

        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        String rispostaRider = (String) ios.readObject();
    }
    public static ArrayList<String> getRiderConnessi() {
        return riderConnessi;
    }

    // se la socket è connessa invia la richiesta al rider connesso su quella socket
    // e aspetta la risposta
    // se il rider accetta allora gli viene inviato il codice dell'ordine

    //public boolean getConnesso(){return connesso;}

        public void run () {
            try {

                ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
                // controllo che l'id del rider è presente del DB
                Rider r = (Rider) ios.readObject();
                ControllaID check = new ControllaID();
                Model.Rider ret = check.controllaIDQuery(r.getIdRider());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                if (ret == null) {
                    //Manda l'oggetto rider null
                    oos.writeObject(null);
                    System.out.println("Non ci sono rider con questo ID");
                } else {
                    //Manda l'oggetto rider creato
                    System.out.println("rider connesso[" + ret.getIdRider() + "]: " + ret.getNome() + " " + ret.getCognome());
                    oos.writeObject("ok");
                    //deve occuparsi di gestire i rider
                    riderConnessi.add(ret.getNome());
                    //connesso=true;
                }

                socket.close();
                //connesso=false;
                System.out.println("socket chiusa");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("failed");
                e.printStackTrace();
            }
        }

    };



