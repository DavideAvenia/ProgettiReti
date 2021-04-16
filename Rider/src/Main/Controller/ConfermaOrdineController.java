package Controller;

import View.ConfermaOrdine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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


    private ConfermaOrdineController(Socket s) throws IOException {
        socket = s;

    }

    public static ConfermaOrdineController getInstanza() throws IOException {
        if (instanza == null) {
            System.out.println("istanza non esiste");
        }
        return instanza;
    }

    public static ConfermaOrdineController getInstanza(Socket s) throws IOException {
        if (instanza == null) {
            instanza = new ConfermaOrdineController(s);
        }
        return instanza;
    }


    public void mostra() throws Exception {
        ConfermaOrdine confermaordine = new ConfermaOrdine();
        confermaordine.start(new Stage());
    }


    public void conferma() throws IOException{

        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(1);
        bw.flush();
    }

    public void annulla() throws IOException{
        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);

        bw.write(0);
        bw.flush();
    }
}



