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
    private static Rider r;
    private static Socket so;
    // il costruttore prende la socket che è stata creata e la salva
    // stanzia il canali di comunicazione di lettura e scrittura con il rider
    // infine chiama run
    public RiderHandler(Socket s) throws Exception {
        socket = s;
        so=s;
        run();
    }

        public void run () {
            try {

                ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
                // controllo che l'id del rider è presente del DB
                r = (Rider) ios.readObject();
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
                    // viene inviato un ok al rider
                    // per confermare che l'id è nel DB, quindi viene aggiunto
                    // alla lista di rider connessi
                    oos.writeObject("ok");
                    riderConnessi.add(ret.getNome());
                }

                socket.close();
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

    public Socket getSocket() {
        return socket;
    }

    public static void removeRider(Rider r){
        riderConnessi.remove(r.getNome());
    }

    public static Rider getRider(){return r;}

    public static boolean inviaOrdine() throws IOException, ClassNotFoundException {
        ConnessioneController cc = null;
        ArrayList<Ordine> ordini = cc.getOrdiniRicevuti();

        ObjectOutputStream oos = new ObjectOutputStream(so.getOutputStream());
        oos.writeObject(ordini.get(0));

        ObjectInputStream ios = new ObjectInputStream(so.getInputStream());
        String rispostaRider = (String) ios.readObject();

        //se il rider accetta l'ordine, allora il rider viene rimosso dalla lista di
        // rider connessi e l'ordine rimosso dalla lista di ordini
        if(rispostaRider.equals("conferma")){
            riderConnessi.remove(r.getNome());
            cc.removeOrdine(ordini.get(0));
            return true;
        }
        return false;
    }

    public static ArrayList<String> getRiderConnessi() {
        return riderConnessi;
    }

    };



