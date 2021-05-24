package Controller;

import Model.Ordine;
import View.ConfermaOrdine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.Socket;
import java.util.Set;

import static java.lang.Thread.sleep;

public class ConfermaOrdineController {


    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private static ConfermaOrdineController instanza = null;
    private BufferedReader in;
    private PrintWriter out;
    private String idRider;
    private String idOrdine;
    private String stato;

    private ConfermaOrdineController(Socket s, String i) throws IOException {
        socket = s;
        idRider = i;
    }

    public boolean checkRichieste() throws IOException, ClassNotFoundException {
        ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());
        Ordine ordine = (Ordine) ios.readObject();
        // deve prendere l'id dell'ordine che però non c'è nella classe??
        return true;
    }

    public static ConfermaOrdineController getInstanza() throws IOException {
        if (instanza == null) {
            System.out.println("istanza non esiste");
        }
        return instanza;
    }

    public static ConfermaOrdineController getInstanza(Socket s,String i) throws IOException {
        if (instanza == null) {
            instanza = new ConfermaOrdineController(s,i);
        }
        return instanza;
    }


    public void mostra() throws Exception {
        ConfermaOrdine confermaordine = new ConfermaOrdine();
        confermaordine.start(new Stage());
    }


    public void conferma() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("conferma");
        System.out.println("ordine confermato");
        sleep(1000);
        if(checkRichieste())
        {
            mostra();
        }
    }

    public String getIdOrdine(){
        return idOrdine;
    }


    public void annulla() throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("annulla");
        System.out.println("ordine annullato");
        if(checkRichieste())
        {
            mostra();
        }
    }
}



