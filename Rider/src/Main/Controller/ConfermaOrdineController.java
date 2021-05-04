package Controller;

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

public class ConfermaOrdineController {


    private String ip = "localhost";
    private int port = 30000;
    private Socket socket;
    private static ConfermaOrdineController instanza = null;
    private BufferedReader in;
    private PrintWriter out;
    private String idRider;
    private String idOrdine;
    private String idRistorante;
    private String stato;

    private ConfermaOrdineController(Socket s, String i) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(new BufferedWriter(osw), true);
        idRider = i;
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


    public void conferma() throws IOException{
        out.write(idRider + "\n");
        out.flush();

        idOrdine = in.readLine();
        idRistorante = in.readLine();
        System.out.println("id ordine: " + idOrdine);
    }

    public String getIdOrdine(){
        return idOrdine;
    }

    public String getIdRistorante(){
        return idRistorante;
    }

    public void consegna() throws Exception{
        out.write("consegnato\n");
        out.flush();
    }

    public void annulla() throws IOException{
        out.write("annulla\n");
        out.flush();
    }
}



